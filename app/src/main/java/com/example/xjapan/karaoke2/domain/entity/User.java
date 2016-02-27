package com.example.xjapan.karaoke2.domain.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xjapan on 16/02/24.
 */
@Table
public class User {
    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("account_id")
    public int accountId;

    @Column(indexed = true)
    @SerializedName("name")
    public String name;

    public User() {}
}
