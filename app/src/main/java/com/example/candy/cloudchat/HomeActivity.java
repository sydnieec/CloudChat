package com.example.candy.cloudchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button login = (Button) findViewById(R.id.Bdisplay);
        login.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
               EditText a= (EditText) findViewById(R.id.TFusername);
                String str= a.getText().toString();
                Intent i= new Intent (HomeActivity.this, Display.class);
                i.putExtra("Username",str );
                startActivity(i);
            }});
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
