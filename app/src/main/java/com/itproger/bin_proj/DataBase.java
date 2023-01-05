package com.itproger.bin_proj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class DataBase extends SQLiteOpenHelper {

    private static final String db_name = "requests";
    private static final int db_version = 1;

    private static final String db_table = "requests_BIN";
    private static final String db_column1 = "search";
    private static final String db_column2 = "scheme";
    private static final String db_column3 = "type";
    private static final String db_column4 = "brand";
    private static final String db_column5 = "country_alpha2";
    private static final String db_column6 = "country_name";
    private static final String db_column7 = "bank_name";
    private static final String db_column8 = "bank_city";
    private static final String db_column9 = "bank_url";
    private static final String db_column10 = "bank_phone";
    private static final String db_column11 = "time";

    public DataBase(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, " +
                        "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                db_table, db_column1, db_column2, db_column3, db_column4, db_column5, db_column6, db_column7, db_column8, db_column9, db_column10, db_column11);

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXISTS %s", db_table);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertData(String search, String scheme, String type, String brand, String country_alpha2, String country_name, String bank_name, String bank_city, String bank_url, String bank_phone, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(db_column1, search);
        values.put(db_column2, scheme);
        values.put(db_column3, type);
        values.put(db_column4, brand);
        values.put(db_column5, country_alpha2);
        values.put(db_column6, country_name);
        values.put(db_column7, bank_name);
        values.put(db_column8, bank_city);
        values.put(db_column9, bank_url);
        values.put(db_column10, bank_phone);
        values.put(db_column11, time);
        db.insertWithOnConflict(db_table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<String> getAllRequests() {
        ArrayList<String> allRequests = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table, new String[]{db_column1, db_column2, db_column3, db_column4, db_column5, db_column6, db_column7, db_column8, db_column9, db_column10, db_column11}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(db_column1);
            int index2 = cursor.getColumnIndex(db_column2);
            int index3 = cursor.getColumnIndex(db_column3);
            int index4 = cursor.getColumnIndex(db_column4);
            int index5 = cursor.getColumnIndex(db_column5);
            int index6 = cursor.getColumnIndex(db_column6);
            int index7 = cursor.getColumnIndex(db_column7);
            int index8 = cursor.getColumnIndex(db_column8);
            int index9 = cursor.getColumnIndex(db_column9);
            int index10 = cursor.getColumnIndex(db_column10);
            int index11 = cursor.getColumnIndex(db_column11);
            allRequests.add("BIN/IIN: " + cursor.getString(index1) + "\nSCHEME / NETWORK: " + cursor.getString(index2)
                    + "\nTYPE : " + cursor.getString(index3) + "\nBRAND : " + cursor.getString(index4)
                    + "\nCOUNTRY : " + cursor.getString(index5) + " " + cursor.getString(index6)
                    + "\nBANK : " + cursor.getString(index7) + ", " + cursor.getString(index8) + "\n" + cursor.getString(index9) + "\n" + cursor.getString(index10) + "\n\n         " + cursor.getString(index11));
        }
        Collections.reverse(allRequests);
        cursor.close();
        db.close();
        return allRequests;
    }

    public Model_BIN getRequest() {
        Model_BIN model_bin = new Model_BIN();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table, new String[]{db_column1, db_column2, db_column3, db_column4, db_column5, db_column6, db_column7, db_column8, db_column9, db_column10, db_column11}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(db_column1);
            int index2 = cursor.getColumnIndex(db_column2);
            int index3 = cursor.getColumnIndex(db_column3);
            int index4 = cursor.getColumnIndex(db_column4);
            int index5 = cursor.getColumnIndex(db_column5);
            int index6 = cursor.getColumnIndex(db_column6);
            int index7 = cursor.getColumnIndex(db_column7);
            int index8 = cursor.getColumnIndex(db_column8);
            int index9 = cursor.getColumnIndex(db_column9);
            int index10 = cursor.getColumnIndex(db_column10);
            int index11 = cursor.getColumnIndex(db_column11);

            model_bin.setCheck_BIN(cursor.getString(index1));
            model_bin.setScheme(cursor.getString(index2));
            model_bin.setType(cursor.getString(index3));
            model_bin.setBrand(cursor.getString(index4));
            model_bin.setCountry_alpha2(cursor.getString(index5));
            model_bin.setCountry_name(cursor.getString(index6));
            model_bin.setBank_name(cursor.getString(index7));
            model_bin.setBank_url(cursor.getString(index8));
            model_bin.setBank_phone(cursor.getString(index9));
            model_bin.setBank_city(cursor.getString(index10));
            model_bin.setTime(cursor.getString(index11));
        }
        cursor.close();
        db.close();
        return model_bin;
    }
}