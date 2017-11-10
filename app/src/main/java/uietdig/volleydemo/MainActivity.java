package uietdig.volleydemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String SERVER_URL = "http://brightacademy.in/currentAffairsSearch/?query=";
    private static final String USER_DETAILS_ENDPOINT = "http://brightacademy.in/users/23/";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, SERVER_URL+"typhoon attack", null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.v("MainActivity", "testingServerSuccessResponse: "+ response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("MainActivity", "testingServerErrorResponse: "+ error);
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                try {
//                    Map<String, String> headers = new HashMap<>();
//                    String key = "Authorization";
//                    headers.put(key, "Token " + "47544bd17b86dfd574d6a1eff843d0dfaa7a2a88");
//                    return headers;
//                } catch (Exception e) {
//                }
//                return super.getParams();
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//        requestQueue.add(jsonObjectRequest);

        HashMap<String, String > contactDetails = new HashMap();
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
        requestQueue.add(jsonObjectRequest);
    }
}
