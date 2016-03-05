package com.example.xjapan.karaoke2;

import android.app.Application;

import com.example.xjapan.karaoke2.db.dao.DAOInjector;

/**
 * Created by xjapan on 16/03/04.
 */
public class Karaoke2 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DAOInjector.inject(this);
    }

    @Override
    public void onTerminate() {
        DAOInjector.leave();
        super.onTerminate();
    }
}
