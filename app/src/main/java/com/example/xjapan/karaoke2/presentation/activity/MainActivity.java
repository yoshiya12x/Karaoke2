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

package com.example.xjapan.karaoke2.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.domain.entity.User;
import com.example.xjapan.karaoke2.presentation.activity.initialization.InitialUseActivity;
import com.example.xjapan.karaoke2.presentation.activity.suggestion.SuggestionActivity;
import com.example.xjapan.karaoke2.usecase.initialization.CheckRegisteredUserUseCase;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private SpannableStringBuilder sb;

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context.getApplicationContext(), MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText roomNameEditText = (EditText) findViewById(R.id.roomNameEditText);
        sb = (SpannableStringBuilder) roomNameEditText.getText();
        Button roomInButton = (Button) findViewById(R.id.roomInButton);
        roomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sb.toString().equals("")) {
                    invokeRoomIn();
                }

            }
        });
        Button roomCreateButton = (Button) findViewById(R.id.roomCreateButton);
        roomCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sb.toString().equals("")) {
                    invokeCreateRoom();
                }
            }
        });

    }

    public void invokeRoomIn() {
//        AppClient.getService().roomIn(Integer.parseInt(userInfo.get(0)), sb.toString(), new Callback<User>() {
//            @Override
//            public void success(User successUserInfo, Response response) {
//                Intent intent = new Intent(context, SuggestionActivity.class);
//                intent.putExtra("roomName", sb.toString());
//                intent.putExtra("account_id", Integer.parseInt(userInfo.get(0)));
//                context.startActivity(intent);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(context, "そのルームはありません", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void invokeCreateRoom() {
//        AppClient.getService().createRoom(sb.toString(), Integer.parseInt(userInfo.get(0)), new Callback<User>() {
//            @Override
//            public void success(User successUserInfo, Response response) {
//                Intent intent = new Intent(context, SuggestionActivity.class);
//                intent.putExtra("roomName", sb.toString());
//                intent.putExtra("account_id", Integer.parseInt(userInfo.get(0)));
//                startActivity(intent);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(context, "すでにそのルームはあります", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
