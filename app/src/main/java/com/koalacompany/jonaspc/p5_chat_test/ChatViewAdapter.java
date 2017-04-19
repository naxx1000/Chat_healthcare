package com.koalacompany.jonaspc.p5_chat_test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewAdapter.ViewHolder>{

    private List<DataProvider> data;
    private LinearLayout borderLayout;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView messageView,timestampView,authorView;
        public ViewHolder(View view){
            super(view);
            borderLayout = (LinearLayout)view.findViewById(R.id.messageBorderLayout);
            messageView = (TextView)view.findViewById(R.id.messageTV);
            timestampView = (TextView)view.findViewById(R.id.timestampTV);
            authorView = (TextView)view.findViewById(R.id.authorTV);
        }
    }

    public ChatViewAdapter(List<DataProvider> myData){
        this.data = myData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataProvider dataProvider = data.get(position);

        holder.messageView.setText(dataProvider.getMessage());
        holder.timestampView.setText(dataProvider.getTimestamp());
        holder.authorView.setText(dataProvider.getAuthor());
        if(dataProvider.getAuthor().equals("Me")){
            borderLayout.setBackgroundColor(Color.rgb(100,200,100));
        }else{
            borderLayout.setBackgroundColor(Color.rgb(70,70,255));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
