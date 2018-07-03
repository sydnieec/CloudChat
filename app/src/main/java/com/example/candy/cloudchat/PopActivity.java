package com.example.candy.cloudchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


public class PopActivity extends Activity {
    Button buttoncreateroom;
    EditText createroomname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        buttoncreateroom= (Button) findViewById(R.id.buttonaddroom);
        createroomname= (EditText) findViewById(R.id.createroomname);

        buttoncreateroom.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v) {
                    if (createroomname.getText().length()<1 || createroomname.getText().length()>10) {
                        createroomname.setError("Name must be 1-10 characters");
                    } else {
                        String str= createroomname.getText().toString();
                        Intent i= new Intent (PopActivity.this, Display.class);
                        i.putExtra("chatroom",str );
                        startActivity(i);

                    }
            }});


        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height= dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int)(height*.4));

        WindowManager.LayoutParams params= getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x= 0;
        params.y=-20;
        getWindow().setAttributes(params);


    }
}
