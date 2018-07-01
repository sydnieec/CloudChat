package com.example.candy.cloudchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.EditText;

public class chats extends AppCompatActivity {
  @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.chats);
      String username =getIntent().getStringExtra("Username");
      TextView tv= (TextView) findViewById(R.id.TVusername);
      tv.setText(username);
  }

}


