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

package com.example.xjapan.karaoke2.presentation.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.presentation.activity.BaseActivity;
import com.example.xjapan.karaoke2.presentation.activity.suggestion.SuggestionActivity;
import com.example.xjapan.karaoke2.usecase.main.CreateRoomUseCase;
import com.example.xjapan.karaoke2.usecase.main.EnterRoomUseCase;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.roomNameEditText)
    EditText roomNameEdit;

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context.getApplicationContext(), MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        roomNameEdit = (EditText) findViewById(R.id.roomNameEditText);

        Button roomInButton = (Button) findViewById(R.id.roomInButton);
        roomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = roomNameEdit.getText().toString();

                if (!TextUtils.isEmpty(name)) {
                    new EnterRoomUseCase().apply(name);
                }
            }
        });
        Button roomCreateButton = (Button) findViewById(R.id.roomCreateButton);
        roomCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = roomNameEdit.getText().toString();

                if (!TextUtils.isEmpty(name)) {
                    new CreateRoomUseCase().apply(name);
                }
            }
        });
    }

    @Subscribe
    public void onEvent(EnterRoomUseCase.OnEnteredRoomEvent event) {
        Intent intent = SuggestionActivity.createIntent(this, event.getName());
        startActivity(intent);
    }

    @Subscribe
    public void onEvent(EnterRoomUseCase.OnEnterRoomFailureEvent event) {
        switch (event.getKind()) {
            case Network:
                showErrorSnack(roomNameEdit, R.string.error__usecase__enter_room__network);
                break;
            case Missing:
                showErrorSnack(roomNameEdit, R.string.error__usecase__enter_room__missing);
                break;
            case DatabaseIO:
                showErrorSnack(roomNameEdit, R.string.error__usecase__enter_room__database_io);
                break;
            default:
                throw new AssertionError("No consistency in OnCreateUserFailureEvent");
        }
    }

    @Subscribe
    public void onEvent(CreateRoomUseCase.OnCreatedRoomEvent event) {
        Intent intent = SuggestionActivity.createIntent(this, event.getName());
        startActivity(intent);
    }

    @Subscribe
    public void onEvent(CreateRoomUseCase.OnCreateRoomFailureEvent event) {
        switch (event.getKind()) {
            case Network:
                showErrorSnack(roomNameEdit, R.string.error__usecase__create_room__network);
                break;
            case DatabaseIO:
                showErrorSnack(roomNameEdit, R.string.error__usecase__create_room__database_io);
                break;
            case Unknown:
                showErrorSnack(roomNameEdit, R.string.error__usecase__unknown);
                break;
            default:
                throw new AssertionError("No consistency in OnCreateUserFailureEvent");
        }
    }
}
