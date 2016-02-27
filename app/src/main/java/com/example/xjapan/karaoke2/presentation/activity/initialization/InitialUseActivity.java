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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.presentation.activity.BaseActivity;
import com.example.xjapan.karaoke2.presentation.activity.main.MainActivity;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.usecase.initialization.CheckRegisteredUserUseCase;
import com.example.xjapan.karaoke2.usecase.initialization.CreateUserUseCase;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InitialUseActivity extends BaseActivity {

    private static final String TAG = InitialUseActivity.class.getSimpleName();

    @Bind(R.id.userNameEditText)
    EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_use);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title__initial_use__text__registration_display);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new CheckRegisteredUserUseCase().apply();
    }

    @Subscribe
    public void onEvent(CheckRegisteredUserUseCase.OnExistEvent event) {
        startActivity(MainActivity.createIntent(this));
        finish();
    }

    @Subscribe
    public void onEvent(CheckRegisteredUserUseCase.OnNotExistEvent event) {
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
                showErrorSnack(nameEdit, R.string.error__usecase__create_user__network);
                break;
            case DatabaseIO:
                showErrorSnack(nameEdit, R.string.error__usecase__create_user__database_io);
                break;
            case Unknown:
                showErrorSnack(nameEdit, R.string.error__usecase__unknown);
                break;
            default:
                throw new AssertionError("No consistency in OnCreateUserFailureEvent");
        }
    }
}
