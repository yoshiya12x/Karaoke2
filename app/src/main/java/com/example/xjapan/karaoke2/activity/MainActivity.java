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
import com.example.xjapan.karaoke2.db.dao.UserDAO;
import com.example.xjapan.karaoke2.model.User;
import com.example.xjapan.karaoke2.util.AppClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private String roomName;
    private EditText roomNameEditText;
    private final UserDAO dao = UserDAO.get();
    private final User user = dao.findFirst();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        //ユーザ登録情報が端末内に保存されていなければ初期画面に飛ばす
        if (user == null) {
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
        AppClient.getService().roomIn(user.accountId, roomName, new Callback<User>() {
            @Override
            public void success(User resUser, Response response) {
                MainActivity.this.startActivity(SuggestionActivity.createIntent(MainActivity.this, roomName, user.accountId));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, R.string.toast_no_room, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void invokeCreateRoom() {
        AppClient.getService().createRoom(roomName, user.accountId, new Callback<User>() {
            @Override
            public void success(User resUser, Response response) {
                MainActivity.this.startActivity(SuggestionActivity.createIntent(MainActivity.this, roomName, user.accountId));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, R.string.toast_already_room, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Intent createIntent(Context context) {
        return new Intent(context.getApplicationContext(), MainActivity.class);
    }
}
