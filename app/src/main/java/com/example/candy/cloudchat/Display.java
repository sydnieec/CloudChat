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
import android.widget.Button;



/**
 * Created by candy on 6/26/2018.
 */

public class Display extends AppCompatActivity {
    private ListView lvchatroomname;
    private ChatroomAdapter adapter;
    private List<Chatroom> mchatroomList;
    int [] IMAGES = {R.drawable.donut, R.drawable.milk, R.drawable.watermelon};
    Button buttonaddroom;
    int k=0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==0 && resultCode == Activity.RESULT_OK) {
            String username = data.getStringExtra(("Username"));
            String chatroom = data.getStringExtra("chatroom");
            k++;
            mchatroomList.add(new Chatroom(k,chatroom,1));
        }
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
                //this does something
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





    }

}
