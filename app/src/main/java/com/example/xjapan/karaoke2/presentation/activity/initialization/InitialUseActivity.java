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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.domain.entity.User;
import com.example.xjapan.karaoke2.presentation.activity.MainActivity;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.usecase.common.FailureCallback;
import com.example.xjapan.karaoke2.usecase.common.RetrofitSuccessEvent;
import com.example.xjapan.karaoke2.usecase.common.SuccessCallback;
import com.example.xjapan.karaoke2.usecase.initialization.CreateUserUseCase;

import retrofit.RetrofitError;

public class InitialUseActivity extends AppCompatActivity {

    private EditText nameEdit;

    public static Intent createIntent(Context context) {
        return new Intent(context.getApplicationContext(), InitialUseActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_use);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登録画面");
        setSupportActionBar(toolbar);
        nameEdit = (EditText) findViewById(R.id.userNameEditText);
        Button userRegisterButton = (Button) findViewById(R.id.userRegisterButton);

        userRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateUserUseCase().applyAsync(nameEdit.getText().toString(), new SuccessCallback<RetrofitSuccessEvent<User>>() {
                    @Override
                    public void onSuccess(RetrofitSuccessEvent<User> success) {
                        Intent intent = MainActivity.createIntent(InitialUseActivity.this);
                        startActivity(intent);
                        finish();
                    }
                }, new FailureCallback<RetrofitError>() {
                    @Override
                    public void onFailure(RetrofitError error) {

                    }
                });
            }
        });
    }
}
