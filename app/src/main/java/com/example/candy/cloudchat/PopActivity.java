package com.example.candy.cloudchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PopActivity extends Activity {
    private static final String TAG = PopActivity.class.getName();
    Button buttoncreateroom;
    EditText createroomname;
    private String url= "http://ec2-13-59-209-87.us-east-2.compute.amazonaws.com:4000/createChatroom";
    private StringRequest MyStringRequest;
    private RequestQueue MyRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        buttoncreateroom= (Button) findViewById(R.id.buttonaddroom);
        createroomname= (EditText) findViewById(R.id.createroomname);
        buttoncreateroom.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v) {
                    if (createroomname.getText().length()<1 || createroomname.getText().length()>10) {
                        createroomname.setError("Name must be 1-10 characters");
                    } else {
                        String str= createroomname.getText().toString();
                        sendPost(str);
                    }
            }});


        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height= dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int)(height*.4));

        WindowManager.LayoutParams params= getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x= 0;
        params.y=-20;
        getWindow().setAttributes(params);


    }
    private void  sendPost(final String chatroomName){
        MyRequestQueue= Volley.newRequestQueue(this);
        MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getJSONObject("status").getString("message");
                    if (message.equals("Error the room exist already")) {
                        Toast errorToast = Toast.makeText(PopActivity.this, message.toString(), Toast.LENGTH_SHORT);
                        errorToast.show();
                    }else {
                        Intent i= new Intent (PopActivity.this, Display.class);
                        String username =getIntent().getStringExtra("Username");
                        setResult(Activity.RESULT_OK,
                                new Intent().putExtra("Username",username));
                        finish();

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error" + error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("chatroomName", chatroomName);
                return MyData;

            };

        };

        MyRequestQueue.add(MyStringRequest);
    }
}
