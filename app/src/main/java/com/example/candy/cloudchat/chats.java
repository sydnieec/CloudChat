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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;


public class chats extends AppCompatActivity {
  private ListView lvMessages;
  private MessageAdapter adapter;
  private List<message> mMessageList;
  EditText message_edit;
  int l= 0;
  private String url= "http://ec2-13-59-209-87.us-east-2.compute.amazonaws.com:4000/connectToRoom";
  private RequestQueue mRequestQueue;
  private StringRequest stringRequest;
  private static final String TAG =chats.class.getName();

  private Socket mSocket;

  {
    try {
      mSocket = IO.socket("http://ec2-13-59-209-87.us-east-2.compute.amazonaws.com:4000");
    } catch (URISyntaxException e) {
    }
  }

    private void attemptSend() throws JSONException {
      String chatroomName =getIntent().getStringExtra("chatroomName");
      final String username = getIntent().getStringExtra("Username");


      JSONObject jsmessage = new JSONObject();
      jsmessage.put("room", chatroomName);
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("message",message_edit.getText().toString() );
      jsonObj.put("username", username);
      jsmessage.put("data", jsonObj);
    // Log.i(TAG, jsmessage.toString());

       mSocket.emit("send",jsmessage);
    }
  private Emitter.Listener onNewMessage = new Emitter.Listener() {
    @Override
    public void call(final Object... args) {
      chats.this.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          JSONObject data = (JSONObject) args[0];

          try {
            String message1 = data.getJSONObject("data").getString("message");
            String username= data.getJSONObject("data").getString("username");
            l++;
            mMessageList.add(new message(l, username,message1));
            adapter = new MessageAdapter(getApplicationContext(), mMessageList);
            lvMessages.setAdapter(adapter);
            lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
          } catch (JSONException e) {
            return;
          }
          //add message to view
        }
      });
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chats);
    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    final String username = getIntent().getStringExtra("Username");
      String chatroomName =getIntent().getStringExtra("chatroomName");
      message_edit = (EditText) findViewById(R.id.message_edit);
    Button send_button = (Button) findViewById(R.id.send_button);
    sendRequestAndPrintResponse();
      mSocket.emit("joinRoom",chatroomName);
    mSocket.on("message",onNewMessage);
    mSocket.connect();
    send_button.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {

        if (message_edit.getText().length() < 1 || message_edit.getText().length() >60) {
          message_edit.setError("Message between 1-60 characters");

        } else {
          message_edit.setText("");

          try {
            attemptSend();
          } catch (JSONException e) {
            e.printStackTrace();
          }
//          l++;
//          mMessageList.add(new message(l, username, message_edit.getText().toString()));
//          message_edit.setText("");
//          adapter = new MessageAdapter(getApplicationContext(), mMessageList);
//          lvMessages.setAdapter(adapter);
//          lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
        }
      }
    });


    lvMessages = (ListView) findViewById(R.id.MessageListView);

    mMessageList = new ArrayList<>();

    //Init Adapter
    adapter = new MessageAdapter(getApplicationContext(), mMessageList);
    lvMessages.setAdapter(adapter);


  }
  private void sendRequestAndPrintResponse() {
    mRequestQueue = Volley.newRequestQueue(this);
    stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

      @Override
      public void onResponse(String response) {
        try {
          JSONObject obj = new JSONObject(response);
          String success = obj.getJSONObject("status").getString("type");
          if (success.equals("Success")){
            JSONObject o = obj.getJSONObject("data");
            JSONArray jArray = o.getJSONArray("messages");
            for (int i = 0; i < jArray.length(); i++)
            {
              String content = jArray.getJSONObject(i).getString("content");
              String username=jArray.getJSONObject(i).getString("username");
              l++;
              mMessageList.add(new message(l, username,content));
              adapter = new MessageAdapter(getApplicationContext(), mMessageList);
              lvMessages.setAdapter(adapter);
              lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);

            }

          }else {
            Toast errorToast = Toast.makeText(chats.this,"Chatroom does not exist.", Toast.LENGTH_SHORT);
            errorToast.show();
          }
        } catch (Exception e){
          e.printStackTrace();
        }

      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.i(TAG, "Error: " + error.toString());
      }
    }
    ){
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
          Map<String, String>  params = new HashMap<String, String>();
          String chatroomName =getIntent().getStringExtra("chatroomName");
        params.put("chatroomName", chatroomName);


          return params;
        }
    };
    mRequestQueue.add(stringRequest);
  }


}


