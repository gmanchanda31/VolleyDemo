package uietdig.volleydemo;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String SERVER_URL = "http://brightacademy.in/currentAffairsSearch/?query=";
    private static final String USER_DETAILS_ENDPOINT = "http://brightacademy.in/users/23/";
    private Context mContext;
    MaterialSearchView searchView;
    RecyclerView mRecyclerView;
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.errorImage);
        mRecyclerView = findViewById(R.id.feedRecyclerView);


        mContext = this;
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
                mImageView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, SERVER_URL+query, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try{
                                    Log.v("MainActivity", "testingServerErrorResponse: "+ response.getString("results"));
                                    Log.v("MainActivity", "testingServerErrorResponse: "+ response);
                                    JSONObject resultJSONObject = response.getJSONObject("results");
                                    JSONArray currentAffairJOSNArray = resultJSONObject.getJSONArray("Tuesday Aug 01, 2017");
                                    ArrayList<ModelFields> mModelList = new ArrayList();
                                    ModelFields mModelObject;
                                    for(int i = 0; i < currentAffairJOSNArray.length(); i++)
                                    {
                                        JSONObject object = currentAffairJOSNArray.getJSONObject(i);
                                        JSONObject feildsObject = object.getJSONObject("fields");
                                        mModelObject = new ModelFields(feildsObject);
                                        mModelList.add(mModelObject);
                                        Log.v("MainActivity", "testingServerErrorResponse: "+ mModelList.get(i).getDescription());
                                    }

                                    FeedAdapter mFeedAdapter = new FeedAdapter(mModelList);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                                    mRecyclerView.setLayoutManager(layoutManager);
                                    mRecyclerView.setAdapter(mFeedAdapter);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                    Log.i("hello the best","world");
                                    mRecyclerView.setVisibility(View.INVISIBLE);
                                    mImageView.setVisibility(View.VISIBLE);
                                    mImageView.setImageResource(R.drawable.nodata);

                                }
                               }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("MainActivity", "testingServerErrorResponse: "+ error);
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        mImageView.setVisibility(View.VISIBLE);
                        mImageView.setImageResource(R.drawable.networkerror);

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
/*
    public void changeNumber(View view) {
        EditText NumberChangeEditText = (EditText) findViewById(R.id.numberchangeditText);
        HashMap<String, String > contactDetails = new HashMap();
        if(NumberChangeEditText.getText().toString().length() == 10) {
            contactDetails.put("mobileNum", NumberChangeEditText.getText().toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, USER_DETAILS_ENDPOINT,
                    new JSONObject(contactDetails),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(mContext, "testingServerSuccessResponse: " + response,Toast.LENGTH_SHORT).show();

                            Log.v("MainActivity", "testingServerErrorResponse: " + response);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("MainActivity", "testingServerErrorResponse: " + error);

                }
            }) {
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
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(jsonObjectRequest);
        }else
            Toast.makeText(mContext, "The number entered is invalid", Toast.LENGTH_SHORT).show();
    }
    */
}
