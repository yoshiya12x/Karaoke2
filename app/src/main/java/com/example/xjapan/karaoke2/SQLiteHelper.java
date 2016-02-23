package com.example.xjapan.karaoke2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xjapan on 16/02/23.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    static final String DatabaseName = "KaraokeDB";
    static final int DatabaseVersion = 7;

    public SQLiteHelper(Context mContext) {
        super(mContext, DatabaseName, null, DatabaseVersion);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //登録していない=0,登録している=1
        db.execSQL("create table `user` ( `userId` integer primary key, `registerFlag` varchar(255), `userName` varchar(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table `user`;");
        db.execSQL("create table `user` ( `userId` integer primary key, `registerFlag` varchar(255), `userName` varchar(255));");
    }
}