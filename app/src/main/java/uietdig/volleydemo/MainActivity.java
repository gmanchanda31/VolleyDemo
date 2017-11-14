package uietdig.volleydemo;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String SERVER_URL = "http://brightacademy.in/currentAffairsSearch/?query=";
    private static final String USER_DETAILS_ENDPOINT = "http://brightacademy.in/users/23/";
    private Context mContext;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;


      /*HashMap<String, String > contactDetails = new HashMap();
        contactDetails.put("mobileNum", "9876543210");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, USER_DETAILS_ENDPOINT,
                new JSONObject(contactDetails),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("MainActivity", "testingServerSuccessResponse: "+ response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("MainActivity", "testingServerErrorResponse: "+ error);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                try {
                    Map<String, String> headers = new HashMap<>();
                    String key = "Authorization";
                    headers.put(key, "Token " + "2bf1fb77bb3f7b1c1085f85bbe2982ef172c453e");
                    return headers;
                } catch (Exception e) {
                }
                return super.getParams();
            }
        };
        int socketTimeout = 50000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        RequestQueue requestQueue =  Volley.newRequestQueue(mContext);
        requestQueue.add(jsonObjectRequest);*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, SERVER_URL+query, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.v("MainActivity", "testingServerSuccessResponse: "+ response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("MainActivity", "testingServerErrorResponse: "+ error);
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        try {
                            Map<String, String> headers = new HashMap<>();
                            String key = "Authorization";
                            headers.put(key, "Token " + "2bf1fb77bb3f7b1c1085f85bbe2982ef172c453e");
                            return headers;
                        } catch (Exception e) {
                        }
                        return super.getParams();
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(jsonObjectRequest);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        searchView.setMenuItem(item);
        searchView.setVoiceSearch(true);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
