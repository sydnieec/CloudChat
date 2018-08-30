package com.example.candy.cloudchat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;
import java.util.HashMap;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG =HomeActivity.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url = "http://www.mocky.io/v2/5b874a32340000db018b58b8";
    private String login_url="http://ec2-13-59-209-87.us-east-2.compute.amazonaws.com:4000/login";
    private StringRequest MyStringRequest;
    private RequestQueue MyRequestQueue;
    private boolean valid_user;


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
                sendPost();

                if (valid_user=false) {
                Toast errorToast = Toast.makeText(HomeActivity.this, "Invalid username or password", Toast.LENGTH_SHORT);
                errorToast.show();

                    }else {
                    //  sendRequestAndPrintResponse();
                    Intent i = new Intent(HomeActivity.this, Display.class);
                    i.putExtra("Username", str);
                    startActivity(i);
                }
            }});
    }
    private void sendPost(){
        MyRequestQueue=Volley.newRequestQueue(this);
         MyStringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String valid = obj.getJSONObject("status").getString("error");
                    Log.i(TAG, "Response: " + valid);
                } catch (Exception e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error" + error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Username:", "bob");
                MyData.put ("Password:", "je" );//Add the data you'd like to send to the server.
                return MyData;

            }

        };


            MyRequestQueue.add(MyStringRequest);
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
