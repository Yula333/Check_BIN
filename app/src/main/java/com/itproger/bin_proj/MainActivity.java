package com.itproger.bin_proj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText search_text;
    private Button search_btn;
    private TextView result_info;

    private DataBase dataBase;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBase = new DataBase(this);
        listView = findViewById(R.id.requests_list);

        search_text = findViewById(R.id.search_text);
        search_btn = findViewById(R.id.search_btn);
        result_info = findViewById(R.id.result_info);

        search_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if((search_text.getText().toString().equals("")) || (search_text.getText().toString().length()<6))
                   Toast.makeText(MainActivity.this, R.string.no_user_input, Toast.LENGTH_SHORT).show();
               else{
                   String text = search_text.getText().toString();
                   String url ="https://lookup.binlist.net/"+text;

                   new GetURLData().execute(url);
               }
           }
       });

        loadAllRequests();
    }

    private void loadAllRequests() {
        ArrayList<String> allRequests = dataBase.getAllRequests();
        if(arrayAdapter == null){
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.result_info, allRequests);
            listView.setAdapter(arrayAdapter);
        } else {
            arrayAdapter.clear();
            arrayAdapter.addAll(allRequests);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private class GetURLData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    return buffer.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();

                try {
                    if (reader != null)
                        reader.close();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            return null;
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(String result){
            super.onPostExecute(result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
//                    String res = search_text.getText().toString()
//                            + "\nSCHEME / NETWORK: " + jsonObject.getString("scheme")
//                            + "\nTYPE : " +  jsonObject.getString("type")
//                            + "\nBRAND : " + jsonObject.getString("brand")
//                            + "\nCOUNTRY : " + jsonObject.getJSONObject("country").getString("name")
//                            + "\nBANK: " + jsonObject.getJSONObject("bank").getString("name");

                    dataBase.insertData(search_text.getText().toString(),jsonObject.getString("scheme"),jsonObject.getString("type"),
                            jsonObject.getString("brand"),jsonObject.getJSONObject("country").getString("name"),jsonObject.getJSONObject("bank").getString("name"));
                    loadAllRequests();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}