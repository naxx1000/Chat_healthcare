package com.koalacompany.jonaspc.p5_chat_test;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Chat_list extends AppCompatActivity {

    String json_response;
    private EditText et_message;
    private RecyclerView chatView;
    private RecyclerView.Adapter myAdapter;
    public List<String> messageList = new ArrayList<>();
    public List<String> timestampList = new ArrayList<>();
    public List<String> authorList = new ArrayList<>();
    private List<DataProvider> myDataSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        et_message = (EditText)findViewById(R.id.et_message);

        chatView = (RecyclerView) findViewById(R.id.chatRV);
        assert chatView != null;
        chatView.setHasFixedSize(true);

        myAdapter = new ChatViewAdapter(myDataSet);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chatView.setLayoutManager(linearLayoutManager);
        chatView.setItemAnimator(new DefaultItemAnimator());
        chatView.setAdapter(myAdapter);

        /*
        chatViewAdapter = new ChatViewAdapter(this,R.layout.message_item_layout);
        listView.setAdapter(chatViewAdapter);
        */

        getJSON();
    }

    public void SendMessage(View view){
        String message = et_message.getText().toString();

        String goodMessage = replaceWord(message,"\\");
        String betterMessage = replaceWord(goodMessage,"'");

        //get timestamp for current message
        Long timestamp = System.currentTimeMillis()/1000;
        //format the timestamp to readable date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM",Locale.getDefault());
        Date date = new Date(timestamp);

        //placeholder for author
        String author = "Me";

        if(!betterMessage.trim().equals("")){
            MyAsyncTask myAsyncTask = new MyAsyncTask(this);
            myAsyncTask.execute(betterMessage,simpleDateFormat.format(date),author);
        }else{
            Toast.makeText(this,"Message cannot be empty",Toast.LENGTH_LONG).show();
        }

        getJSON();

        //clear edit text box for new message
        et_message.setText("");
    }

    public String replaceWord(String message, String word){

        StringBuilder sbMessage = new StringBuilder(message);
        List<Integer> listOfApo = new ArrayList<>();
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

    public void getJSON(){
        new BackgroundTask().execute();
    }

    //parses the JSON into a string using JSONObject method
    public void parseJSON(){
        if(json_response!=null){
            try {
                JSONObject jsonObject = new JSONObject(json_response);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String messageP,timestampP,authorP;
                myDataSet.clear();

                while(count<jsonArray.length()){
                    JSONObject jo = jsonArray.getJSONObject(count);
                    messageP = jo.getString("user_message");
                    timestampP = jo.getString("timestamp");
                    authorP = jo.getString("author");
                    DataProvider dataProvider = new DataProvider(messageP,timestampP,authorP);
                    myDataSet.add(dataProvider);

                    messageList.add(jo.getString("user_message"));
                    timestampList.add(jo.getString("timestamp"));
                    authorList.add(jo.getString("author"));

                    count++;
                }
                if(myAdapter.getItemCount() != 0){
                    chatView.smoothScrollToPosition(myAdapter.getItemCount()-1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>{

        String json_url;

        @Override
        protected String doInBackground(Void... params) {
            //Try to open connection to URL as an asynchronous task.
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                //While loop, as long as input is not null.
                while((json_response = bufferedReader.readLine()) != null){
                    stringBuilder.append(json_response+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            //Defines the url before task is executed
            json_url = "http://shaken-flower.000webhostapp.com/get_data.php";
        }

        @Override
        protected void onPostExecute(String result) {
            json_response = result;
            parseJSON();
            chatView.requestLayout();
            //myAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
