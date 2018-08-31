package com.example.candy.cloudchat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by candy on 6/27/2018.
 */

public class signup extends AppCompatActivity {
    private static final String TAG = signup.class.getName();
    EditText TFsignupemail;
    EditText TFsignupusername;
    EditText TFsignuppass;
    EditText TFsignuppass1;
    private String login_url="http://ec2-13-59-209-87.us-east-2.compute.amazonaws.com:4000/signup";
    private StringRequest MyStringRequest;
    private RequestQueue MyRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TFsignupemail= (EditText) findViewById(R.id.TFsignupemail);
        TFsignupusername= (EditText) findViewById(R.id.TFsignupusername);
        TFsignuppass= (EditText) findViewById(R.id.TFsignuppass);
        TFsignuppass1= (EditText) findViewById(R.id.TFsignuppass1);



        Button login = (Button) findViewById(R.id.Bsignup1);
        login.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                String password= TFsignuppass.getText().toString();
                String password1=TFsignuppass1.getText().toString();
                if(!isValidEmailAddress(TFsignupemail.getText().toString())){
                    TFsignupemail.setError("Enter a valid emaill");
                } else if (TFsignupusername.getText().length()<1 || TFsignupusername.getText().length()>12){
                    TFsignupusername.setError("Enter a valid username");
                }
                else if (!(isValidPassword(TFsignuppass.getText().toString()))){
                    TFsignuppass.setError("# start-of-string\n" +
                            " # a digit must occur at least once\n" +
                            "# a lower case letter must occur at least once\n" +
                            "# an upper case letter must occur at least once\n" +
                            " # no whitespace allowed in the entire string\n" +
                            "# anything, at least eight places though\n");

                } else if (!password.equals(password1)){
                    TFsignuppass1.setError("Passwords must match");
                }
                else {
                    sendPost(TFsignupemail.getText().toString(),TFsignupusername.getText().toString(),password);

                }
            }});
    }
    private void  sendPost(final String strEmail, final String strUsername, final String strPassword){
        MyRequestQueue= Volley.newRequestQueue(this);
        MyStringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getJSONObject("status").getString("message");
                 if (!(message.equals("Signup success"))) {
                        Toast errorToast = Toast.makeText(signup.this, message.toString(), Toast.LENGTH_SHORT);
                        errorToast.show();
                    }else {

                     Intent i = new Intent(signup.this, HomeActivity.class);
                     startActivity(i);
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
                MyData.put("email", strEmail);
                MyData.put ("username", strUsername );
                MyData.put("password", strPassword);
                return MyData;

            };

        };

        MyRequestQueue.add(MyStringRequest);
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean isValidPassword(String password) {
        String ePattern1 = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}";
        Pattern p1 = java.util.regex.Pattern.compile(ePattern1);
        Matcher m = p1.matcher(password);
        return m.matches();
    }
}
