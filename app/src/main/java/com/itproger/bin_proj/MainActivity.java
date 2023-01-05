package com.itproger.bin_proj;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText search_text;
    private Button search_btn;
    private TextView result_info;
    private TextView search_BIN;
    private TextView scheme_BIN;
    private TextView type_BIN;
    private TextView brand_BIN;
    private TextView country_alpha2_BIN;
    private TextView country_BIN;
    private TextView bank_name_BIN;
    private TextView bank_city_BIN;
    private TextView bank_url_BIN;
    private TextView bank_phone_BIN;

    private DataBase dataBase;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private final String TAG = "DEV";

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

        final Context context = this;

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

                Log.i(TAG, dataBase.getRequest().getScheme()+dataBase.getRequest().getCountry_name()+"*********\n**********");
                //Получаем вид с файла preview_dialog.xml, который применим для диалогового окна:
                LayoutInflater li = LayoutInflater.from(context);
                View prev_dialog = li.inflate(R.layout.preview_dialog, null);
                //Создаем AlertDialog
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                //Настраиваем preview_dialog.xml для нашего AlertDialog:
                mDialogBuilder.setView(prev_dialog);
                //Настраиваем отображение поля для ввода текста в открытом диалоге:

                search_BIN = prev_dialog.findViewById(R.id.search_BIN);
                search_BIN.setText(search_text.getText());
                scheme_BIN = prev_dialog.findViewById(R.id.scheme_BIN);
                scheme_BIN.setText(dataBase.getRequest().getScheme());
                type_BIN = prev_dialog.findViewById(R.id.type_BIN);
                type_BIN.setText(dataBase.getRequest().getType());
                brand_BIN =  prev_dialog.findViewById(R.id.brand_BIN);
                brand_BIN.setText(dataBase.getRequest().getBrand());
                country_alpha2_BIN = prev_dialog.findViewById(R.id.country_alpha2_BIN);
                country_alpha2_BIN.setText(dataBase.getRequest().getCountry_alpha2());
                country_BIN =  prev_dialog.findViewById(R.id.country_BIN);
                country_BIN.setText(dataBase.getRequest().getCountry_name());
                bank_name_BIN =  prev_dialog.findViewById(R.id.bank_name_BIN);
                bank_name_BIN.setText(dataBase.getRequest().getBank_name());
                bank_city_BIN =  prev_dialog.findViewById(R.id.bank_city_BIN);
                bank_city_BIN.setText(dataBase.getRequest().getBank_city());
                bank_url_BIN =  prev_dialog.findViewById(R.id.bank_url_BIN);
                bank_url_BIN.setText(dataBase.getRequest().getBank_url());
                bank_phone_BIN =  prev_dialog.findViewById(R.id.bank_phone_BIN);
                bank_phone_BIN.setText(dataBase.getRequest().getBank_phone());

                mDialogBuilder.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                //Создаем AlertDialog:
                AlertDialog alertDialog = mDialogBuilder.create();

                //и отображаем его:
                alertDialog.show();
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

    @SuppressLint("StaticFieldLeak")
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

        String check_BIN, scheme, type, brand, country_alpha2, country_name, bank_name, bank_url, bank_phone, bank_city, time;

        @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                check_BIN = search_text.getText().toString();
            // Считываем значения JSON, проверяем если не пустые, для сохранения в БД
                scheme = jsonObject.optString("scheme").equals("") ? " - " : jsonObject.optString("scheme");
                type  = jsonObject.optString("type").equals("") ? " - " : jsonObject.optString("type");
            // Log.i(TAG, type);
                brand = jsonObject.optString("brand").equals("") ? " - " : jsonObject.optString("brand");
            // Log.i(TAG, brand);
                country_alpha2 = jsonObject.getJSONObject("country").optString("alpha2").equals("") ? " - " : jsonObject.getJSONObject("country").optString("alpha2");
                country_name = jsonObject.getJSONObject("country").optString("name").equals("") ? " - " : jsonObject.getJSONObject("country").optString("name");
                bank_name = jsonObject.getJSONObject("bank").optString("name").equals("") ? " - " : jsonObject.getJSONObject("bank").optString("name");;
                bank_city = jsonObject.getJSONObject("bank").optString("city").equals("") ? " - " : jsonObject.getJSONObject("bank").optString("city");
                bank_url = jsonObject.getJSONObject("bank").optString("url").equals("") ? " - " : jsonObject.getJSONObject("bank").optString("url");
                bank_phone = jsonObject.getJSONObject("bank").optString("phone").equals("") ? " - " : jsonObject.getJSONObject("bank").optString("phone");
                Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
                time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);

                // Добавляем значения в БД
                dataBase.insertData(check_BIN, scheme, type, brand, country_alpha2, country_name, bank_name, bank_city, bank_url, bank_phone, time);
                loadAllRequests();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}