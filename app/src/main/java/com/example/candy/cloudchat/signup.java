package com.example.candy.cloudchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;

/**
 * Created by candy on 6/27/2018.
 */

public class signup extends Activity {
    EditText TFsignupemail;
    EditText TFsignupusername;
    EditText TFsignuppass;
    EditText TFsignuppass1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

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
                    TFsignupemail.setError("Enter a valid email");
                } else if (TFsignupusername.getText().length()<1 || TFsignupusername.getText().length()>12){
                    TFsignupusername.setError("Enter a valid username");
                }
                else if (!(isValidPassword(TFsignuppass.getText().toString()))){
                    TFsignuppass.setError("Password 6-12 Characters ");

                } else if (!password.equals(password1)){
                    TFsignuppass1.setError("Passwords must match");
                }
                else {
                    Intent i = new Intent(signup.this, HomeActivity.class);
                    startActivity(i);
                }
            }});
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean isValidPassword(String password) {
        String ePattern1 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern p1 = java.util.regex.Pattern.compile(ePattern1);
        Matcher m = p1.matcher(password);
        return m.matches();
    }
}
