/*
* アプリ起動時（ユーザ登録後）のルーム作成・入室画面
* （InitialUseActivityから遷移
* 　SuggestionActivityへと遷移）
*
* ルームの作成 or 入室が可能
*
* ルームの作成
* ルーム名を入力してルームを作成
* 既に存在するルーム名の場合はルームを作成せずにアラート
*
* ルームの入室
* ルーム名を入力してルームに入室
* 存在しないルーム名の場合はルームに入室せずアラート
*
* */

package com.example.xjapan.karaoke2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.model.UserInfo;
import com.example.xjapan.karaoke2.sqlite.UserDB;
import com.example.xjapan.karaoke2.util.AppClient;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> userInfo;
    private String roomName;
    private EditText roomNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        UserDB userDB = new UserDB(this);
        userInfo = userDB.selectAll();
        //ユーザ登録情報が端末内に保存されていなければ初期画面に飛ばす
        if (userInfo.size() == 0) {
            MainActivity.this.startActivity(InitialUseActivity.createIntent(MainActivity.this));
        }
        roomNameEditText = (EditText) findViewById(R.id.roomNameEditText);
        Button roomInButton = (Button) findViewById(R.id.roomInButton);
        roomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomName = roomNameEditText.getText().toString();
                if (!roomName.isEmpty()) {
                    invokeRoomIn();
                }
            }
        });
        Button roomCreateButton = (Button) findViewById(R.id.roomCreateButton);
        roomCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomName = roomNameEditText.getText().toString();
                if (!roomName.isEmpty()) {
                    invokeCreateRoom();
                }
            }
        });

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void invokeRoomIn() {
        final int userId = Integer.parseInt(userInfo.get(0));
        AppClient.getService().roomIn(userId, roomName, new Callback<UserInfo>() {
            @Override
            public void success(UserInfo successUserInfo, Response response) {
                MainActivity.this.startActivity(SuggestionActivity.createIntent(MainActivity.this, roomName, userId));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "そのルームはありません", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void invokeCreateRoom() {
        final int userId = Integer.parseInt(userInfo.get(0));
        AppClient.getService().createRoom(roomName, userId, new Callback<UserInfo>() {
            @Override
            public void success(UserInfo successUserInfo, Response response) {
                MainActivity.this.startActivity(SuggestionActivity.createIntent(MainActivity.this, roomName, userId));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "すでにそのルームはあります", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        return intent;
    }
}
