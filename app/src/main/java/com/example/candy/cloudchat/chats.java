package com.example.candy.cloudchat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class chats extends AppCompatActivity {
  private ListView lvMessages;
  private MessageAdapter adapter;
  private List<message> mMessageList;
  EditText message_edit;
  int l= 0;




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chats);
    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    final String username = getIntent().getStringExtra("Username");
    message_edit = (EditText) findViewById(R.id.message_edit);
    Button send_button = (Button) findViewById(R.id.send_button);
    send_button.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {

        if (message_edit.getText().length() < 1 || message_edit.getText().length() >30) {
          message_edit.setError("Message between 1-30 characters");

        } else {
          l++;
          mMessageList.add(new message(l, username, message_edit.getText().toString()));
          message_edit.setText("");
          adapter = new MessageAdapter(getApplicationContext(), mMessageList);
          lvMessages.setAdapter(adapter);
        }
      }
    });


    lvMessages = (ListView) findViewById(R.id.MessageListView);
    mMessageList = new ArrayList<>();

    //Init Adapter
    adapter = new MessageAdapter(getApplicationContext(), mMessageList);
    lvMessages.setAdapter(adapter);


  }


}


