package com.example.candy.cloudchat;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayList;


/**
 * Created by candy on 6/28/2018.
 */



public class ChatroomAdapter extends BaseAdapter{
    private Context mContext;
    private List<Chatroom> mchatroomList;
    //Constructor

    public ChatroomAdapter(Context mContext, List<Chatroom> mchatroomList) {
        this.mContext = mContext;
        this.mchatroomList = mchatroomList;
    }

    @Override
    public int getCount() {
        return mchatroomList.size();
    }

    @Override
    public Object getItem(int position) {
        return mchatroomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
//        if (convertView == null) {
//             v= View.inflate(mContext, R.layout.pic_chat_ppl_list, null);
//        }else {
//             v= convertView;
//        }
        v= View.inflate(mContext, R.layout.pic_chat_ppl_list, null);
        TextView roomname = (TextView) v.findViewById(R.id.chatroomname);
        TextView intpeople= (TextView) v.findViewById(R.id.numberofpeople);
        //Set text for TextView
        roomname.setText(mchatroomList.get(position).getName());
        intpeople.setText(String.valueOf(mchatroomList.get(position).getNumberofpeople()));

        //Save product id to tag
        v.setTag(mchatroomList.get(position).getId());

        return v;
    }
}