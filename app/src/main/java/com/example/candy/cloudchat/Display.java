package com.example.candy.cloudchat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by candy on 6/26/2018.
 */

public class Display extends AppCompatActivity {
    private ListView lvchatroomname;
    private ChatroomAdapter adapter;
    private List<Chatroom> mchatroomList;
    int [] IMAGES = {R.drawable.donut, R.drawable.milk, R.drawable.watermelon};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lvchatroomname = (ListView) findViewById(R.id.Listview_chatroom);
        mchatroomList = new ArrayList<>();

        mchatroomList.add(new Chatroom(1,"hello",2));
        mchatroomList.add(new Chatroom(2,"plzwork",1));
        mchatroomList.add(new Chatroom(3,"wtf",3));


        //Init Adapter
        adapter= new ChatroomAdapter(getApplicationContext(), mchatroomList);
        lvchatroomname.setAdapter(adapter);

        lvchatroomname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //this does something
                Toast.makeText(getApplicationContext(), "Clicked on =" +view.getTag(), Toast.LENGTH_SHORT).show();
                Intent g = new Intent(Display.this, chats.class);
                String username =getIntent().getStringExtra("Username");
                g.putExtra("Username",username);
                startActivity(g);

            }
        });


    }

}
