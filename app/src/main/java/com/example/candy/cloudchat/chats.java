package com.example.candy.cloudchat;

import android.content.Intent;
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
  @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.chats);
    message_edit= (EditText) findViewById(R.id.message_edit);
    Button send_button = (Button) findViewById(R.id.send_button);

    String username =getIntent().getStringExtra("Username");

    lvMessages = (ListView) findViewById(R.id.MessageListView);
    mMessageList = new ArrayList<>();
    mMessageList.add(new message(1,username,"yo"));
    mMessageList.add(new message(2,username,"hey"));
    mMessageList.add(new message(3,username,"fuck youu "));


    //Init Adapter
    adapter= new MessageAdapter(getApplicationContext(), mMessageList);
    lvMessages.setAdapter(adapter);


  }

}



