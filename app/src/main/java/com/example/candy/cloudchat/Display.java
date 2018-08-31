package com.example.candy.cloudchat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;




/**
 * Created by candy on 6/26/2018.
 */

public class Display extends AppCompatActivity {
    private static final String TAG = Display.class.getName();
    private ListView lvchatroomname;
    private ChatroomAdapter adapter;
    private List<Chatroom> mchatroomList;
    int [] IMAGES = {R.drawable.donut, R.drawable.milk, R.drawable.watermelon};
    Button buttonaddroom;
    int k=0;
    Random rand = new Random();
    private RequestQueue mRequestQueue;
    private  StringRequest stringRequest;
    private String url= "http://ec2-13-59-209-87.us-east-2.compute.amazonaws.com:4000/chatrooms";



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==0 && resultCode == Activity.RESULT_OK) {
            String username = data.getStringExtra(("Username"));
            String chatroom = data.getStringExtra("chatroom");
            mchatroomList.clear();
            sendRequestAndPrintResponse();
         //   k++;
        //    int  n = rand.nextInt(10) + 1;
        //    mchatroomList.add(new Chatroom(k,chatroom,n));
        }
    }
    private void sendRequestAndPrintResponse() {
        mRequestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String success = obj.getJSONObject("status").getString("type");
                    if (!(success.equals("Success"))){
                        Toast errorToast = Toast.makeText(Display.this, success.toString(), Toast.LENGTH_SHORT);
                        errorToast.show();
                    }else {
                        JSONObject o = obj.getJSONObject("data");
                        JSONArray jArray = o.getJSONArray("chatrooms");

                        for (int i = 0; i < jArray.length(); i++)
                        {
                            String getChat = jArray.getJSONObject(i).getString("chatroomName");
                            k++;
                            int  n = rand.nextInt(10) + 1;
                            mchatroomList.add(new Chatroom(k,getChat,n));
                        }
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
        });
        mRequestQueue.add(stringRequest);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        final String chatroom = getIntent().getStringExtra("chatroom");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lvchatroomname = (ListView) findViewById(R.id.Listview_chatroom);
        mchatroomList = new ArrayList<>();

        //Init Adapter
        adapter= new ChatroomAdapter(getApplicationContext(), mchatroomList);
        lvchatroomname.setAdapter(adapter);

        lvchatroomname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent g = new Intent(Display.this, chats.class);
                String username =getIntent().getStringExtra("Username");
                g.putExtra("Username",username);
                    startActivity(g);


            }
        });
        buttonaddroom = (Button) findViewById(R.id.buttonaddroom);
        buttonaddroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), PopActivity.class);
                String username =getIntent().getStringExtra("Username");
                i.putExtra("Username",username);

                startActivityForResult(i,0);

            }
        });
        sendRequestAndPrintResponse();





    }

}
