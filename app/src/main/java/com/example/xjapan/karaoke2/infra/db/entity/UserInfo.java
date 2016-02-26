package com.example.xjapan.karaoke2.infra.db.entity;

import java.io.Serializable;

/**
 * Created by xjapan on 16/02/24.
 */
public class UserInfo implements Serializable {
    private int account_id;
    private String name;

    public int getAccountId() {
        return account_id;
    }

    public void setAccountId(int account_id) {
        this.account_id = account_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
