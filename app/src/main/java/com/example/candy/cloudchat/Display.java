package com.example.candy.cloudchat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by candy on 6/26/2018.
 */

public class Display extends Activity {
    String [] NAMES= {"coolchat1", "cootchat2", "litchat"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        ListView listView= (ListView) findViewById(R.id.Listview);
    }

}
