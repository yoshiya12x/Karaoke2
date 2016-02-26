package com.example.xjapan.karaoke2.infra.db.dao;

import android.app.Application;

import com.example.xjapan.karaoke2.infra.db.entity.OrmaDatabase;
import com.github.gfx.android.orma.AccessThreadConstraint;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class DAOInjector {
    private DAOInjector() {}

    public static void inject(Application app) {
        OrmaDatabase orma = OrmaDatabase.builder(app).
                readOnMainThread(AccessThreadConstraint.WARNING).
                writeOnMainThread(AccessThreadConstraint.WARNING).
                build();

        UserDAO.inject(orma);
    }

    public static void leave() {
        UserDAO.leave();
    }
}
