package com.koalacompany.jonaspc.p5_chat_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Chat_list extends AppCompatActivity {

    private List<String> arrayMessage;
    private List<String> arrayAuthor;

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;

    private EditText et_message;

    private List<DataProvider> chatDataSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        et_message = (EditText)findViewById(R.id.et_message);

    }

    public void SendMessage(View view){
        String message = et_message.getText().toString();

        String goodMessage = replaceWord(message,"\\");
        String betterMessage = replaceWord(goodMessage,"'");

        System.out.println(betterMessage);

        MyAsyncTask myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute(betterMessage);
    }

    public String replaceWord(String message, String word){

        StringBuilder sbMessage = new StringBuilder(message);
        List listOfApo = new ArrayList<>();
        int n = 0;

        if(message.contains(word)){
            for (int index = message.indexOf(word);
                 index >= 0;
                 index = message.indexOf(word, index + 1))
            {
                listOfApo.add(index);
                sbMessage.insert(index+n, "\\");
                n++;
            }
        }

        return sbMessage.toString();
    }
}
