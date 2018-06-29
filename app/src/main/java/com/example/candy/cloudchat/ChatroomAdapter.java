package com.example.candy.cloudchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ArrayList;

/**
 * Created by candy on 6/28/2018.
 */


}
public class ChatroomAdapter extends ArrayAdapter<String>  {
    private ArrayList<String>names;
    private Activity context;
    public ChatroomAdapter(@NonNull Activity context,ArrayList<String>names ) {
        super(context,R.layout.contact_row_frag,names);

        this.names = names;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup Parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = view;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.contact_row_frag, Parent, false);
        }
        TextView textView = rowView.findViewById(R.id.nameText);
        TextView date = rowView.findViewById(R.id.dateInfo);
        date.setText("");
        ImageView callStatus = rowView.findViewById(R.id.callStatus);
        callStatus.setImageResource(0);

        textView.setText(names.get(position));

        return rowView;
    }
}