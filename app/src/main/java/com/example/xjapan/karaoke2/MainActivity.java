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

package com.example.xjapan.karaoke2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private SpannableStringBuilder sb;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        context = this;
        UserDB userDB = new UserDB(this);
        final ArrayList<String> userInfo = userDB.selectAll();
        if (userInfo.size() == 0) {
            Intent intentInitialUseActivity = new Intent(this, InitialUseActivity.class);
            this.startActivity(intentInitialUseActivity);
        } else {
            // toolbar.setTitle(userInfo.get(2));
        }
        setSupportActionBar(toolbar);

        EditText roomNameEditText = (EditText) findViewById(R.id.roomNameEditText);
        sb = (SpannableStringBuilder) roomNameEditText.getText();
        Button roomInButton = (Button) findViewById(R.id.roomInButton);
        roomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sb.toString().equals("")) {
                    AppClient.getService().roomIn(Integer.parseInt(userInfo.get(0)), sb.toString(), new Callback<UserInfo>() {
                        @Override
                        public void success(UserInfo successUserInfo, Response response) {
                            Intent intent = new Intent(context, SuggestionActivity.class);
                            intent.putExtra("roomName", sb.toString());
                            intent.putExtra("account_id", Integer.parseInt(userInfo.get(0)));
                            context.startActivity(intent);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //ここにルームがないアラート
                            Log.d("musicRecommendList_test", error.toString());
                        }
                    });
                }

            }
        });
        Button roomCreateButton = (Button) findViewById(R.id.roomCreateButton);
        roomCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sb.toString().equals("")) {//createRoom
                    AppClient.getService().createRoom(sb.toString(), Integer.parseInt(userInfo.get(0)), new Callback<UserInfo>() {
                        @Override
                        public void success(UserInfo successUserInfo, Response response) {
                            Intent intent = new Intent(context, SuggestionActivity.class);
                            intent.putExtra("roomName", sb.toString());
                            intent.putExtra("account_id", Integer.parseInt(userInfo.get(0)));
                            context.startActivity(intent);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //ここにルームがないアラート
                            Log.d("musicRecommendList_test", error.toString());
                        }
                    });
                }
            }
        });

    }

}
