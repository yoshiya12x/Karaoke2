package com.example.xjapan.karaoke2;

import android.app.Application;

import com.example.xjapan.karaoke2.infra.db.dao.DAOInjector;

/**
 * Created by jmatsu on 2016/02/27.
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
