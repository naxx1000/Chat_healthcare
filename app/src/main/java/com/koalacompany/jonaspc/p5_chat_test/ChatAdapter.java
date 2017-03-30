package com.koalacompany.jonaspc.p5_chat_test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private List<DataProvider> chatDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView myTextAuthor, myTextMessage;
        public LinearLayout myLinearLayout;
        public ViewHolder(View view){
            super(view);
            myTextAuthor = (TextView)view.findViewById(R.id.textViewAuthor);
            myTextMessage = (TextView)view.findViewById(R.id.textViewMessage);
            myLinearLayout = (LinearLayout)view.findViewById(R.id.linearLayoutChat);
        }
    }

    //constructor
    public ChatAdapter(List<DataProvider> myDataSet){
        this.chatDataSet = myDataSet;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataProvider dataProvider = chatDataSet.get(position);

        holder.myTextAuthor.setText(dataProvider.getAuthor());
        holder.myTextMessage.setText(dataProvider.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatDataSet.size();
    }
}
