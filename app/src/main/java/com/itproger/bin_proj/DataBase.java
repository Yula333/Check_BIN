package com.itproger.bin_proj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static final String db_name = "requests_db";
    private static final int db_version = 1;

    private static final String db_table = "requests_history";
    private static final String db_column1 = "search";
    private static final String db_column2 = "scheme";
    private static final String db_column3 = "type";
    private static final String db_column4 = "brand";
    private static final String db_column5 = "country";
    private static final String db_column6 = "bank";


    public DataBase(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL);",
                db_table, db_column1, db_column2, db_column3, db_column4, db_column5, db_column6);

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXISTS %s", db_table);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertData(String search, String scheme, String type, String brand, String country, String bank){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(db_column1, search);
        values.put(db_column2, scheme);
        values.put(db_column3, type);
        values.put(db_column4, brand);
        values.put(db_column5, country);
        values.put(db_column6, bank);
        db.insertWithOnConflict(db_table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<String>getAllRequests(){
        ArrayList<String> allRequests = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table, new String[] {db_column1, db_column2, db_column3, db_column4, db_column5, db_column6}, null, null, null, null, null);
        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(db_column1);
            int index2= cursor.getColumnIndex(db_column2);
            int index3 = cursor.getColumnIndex(db_column3);
            int index4 = cursor.getColumnIndex(db_column4);
            int index5= cursor.getColumnIndex(db_column5);
            int index6 = cursor.getColumnIndex(db_column6);
            allRequests.add("BIN/IIN: " + cursor.getString(index1) + "\nSCHEME / NETWORK: " + cursor.getString(index2)
                    + "\nTYPE : " + cursor.getString(index3) + "\nBRAND : " + cursor.getString(index4)
                    + "\nCOUNTRY : " + cursor.getString(index5) + "\nBANK : " + cursor.getString(index6));
        }
        cursor.close();
        db.close();
        return allRequests;
    }
}
