package com.example.xjapan.karaoke2.infra.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xjapan.karaoke2.infra.db.helper.SQLiteHelper;

import java.util.ArrayList;

/**
 * Created by xjapan on 16/02/23.
 */
public class UserDB {
    private String tableName = "user";
    private SQLiteHelper helper;

    public UserDB(Context context) {
        helper = new SQLiteHelper(context);
    }

    public ArrayList<String> selectAll() {
        String[] tableColumn = {"userId", "account_id", "userName"};
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(tableName, tableColumn, null, null, null, null, null);
        boolean mov = cursor.moveToFirst();
        ArrayList<String> query = new ArrayList<>();
        while (mov) {
            query.add(cursor.getString(1));
            query.add(cursor.getString(2));
            mov = cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return query;
    }

    public void insertAll(int account_id, String userName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("account_id", account_id + "");
        insertValues.put("userName", userName);
        long id = db.insert(tableName, "00", insertValues);
        db.close();
    }
}
