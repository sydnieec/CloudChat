package com.example.candy.cloudchat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG =HomeActivity.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url = "http://www.mocky.io/v2/5b874a32340000db018b58b8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button login = (Button) findViewById(R.id.Bdisplay);
        login.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

               EditText a= (EditText) findViewById(R.id.TFusername);
                String str= a.getText().toString();
                sendRequestAndPrintResponse();
                Intent i= new Intent (HomeActivity.this, Display.class);
                i.putExtra("Username",str );
                startActivity(i);
            }});
    }

    private void sendRequestAndPrintResponse() {
        mRequestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj = new JSONObject(response);
                    String pageName = obj.getJSONObject("pageInfo").getString("pageName");
                    JSONArray arr = obj.getJSONArray("posts");
                        String post_id = arr.getJSONObject(0).getString("post_id");
                    Log.i(TAG, "Response: " + pageName+" "+ post_id );

                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.i(TAG, "Error: " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest);
    }


    public void OnButtonClick1(View v)
    {
        if (v.getId() == R.id.Bsignup)
        {
            Intent i = new Intent(HomeActivity.this, signup.class);
            startActivity(i);
        }
    }
}
