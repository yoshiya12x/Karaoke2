/*
* アプリ初回起動時の画面
*（MainActivityへ遷移）
*
* 初回起動時にユーザ名を登録するための画面
* 端末内部にユーザ名等の情報を保管しておくため二度目以降は登録不要
*
* ユーザ名を入力して、登録ボタンを押すことで登録が完了
* 内部でユーザIDが作成されるので、ユーザ名のかぶりが可能
*
* */

package com.example.xjapan.karaoke2.presentation.activity.initialization;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.presentation.activity.MainActivity;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.usecase.initialization.CheckRegisteredUserUseCase;
import com.example.xjapan.karaoke2.usecase.initialization.CreateUserUseCase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class InitialUseActivity extends AppCompatActivity {

    private static final String TAG = InitialUseActivity.class.getSimpleName();

    private EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_use);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title__initial_use__text__registration_display);
        setSupportActionBar(toolbar);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        new CheckRegisteredUserUseCase().apply();
    }

    @Subscribe
    public void onEvent(CheckRegisteredUserUseCase.OnExistEvent event) {
        startActivity(MainActivity.createIntent(this));
        finish();
    }

    @Subscribe
    public void onEvent(CheckRegisteredUserUseCase.OnNotExistEvent event) {
        nameEdit = (EditText) findViewById(R.id.userNameEditText);
        Button userRegisterButton = (Button) findViewById(R.id.userRegisterButton);

        userRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateUserUseCase().apply(nameEdit.getText().toString());
            }
        });

    }

    @Subscribe
    public void onEvent(CreateUserUseCase.OnCreatedUserEvent event) {
        Intent intent = MainActivity.createIntent(this);
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void onEvent(CreateUserUseCase.OnCreateUserFailureEvent event) {
        switch (event.getKind()) {
            case Network:
                Snackbar.make(nameEdit, R.string.error__usecase__create_user__network, Snackbar.LENGTH_SHORT).show();
                break;
            case DatabaseIO:
                Snackbar.make(nameEdit, R.string.error__usecase__create_user__database_io, Snackbar.LENGTH_SHORT).show();
                break;
            case Unknown:
                Snackbar.make(nameEdit, R.string.error__usecase__unknown, Snackbar.LENGTH_SHORT).show();
                break;
            default:
                throw new AssertionError("No consistency in OnCreateUserFailureEvent");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onPause() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onPause();
    }
}
