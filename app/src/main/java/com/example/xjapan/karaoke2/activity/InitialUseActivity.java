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

package com.example.xjapan.karaoke2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.model.User;
import com.example.xjapan.karaoke2.usecase.common.FailureCallback;
import com.example.xjapan.karaoke2.usecase.common.RetrofitSuccessEvent;
import com.example.xjapan.karaoke2.usecase.common.SuccessCallback;
import com.example.xjapan.karaoke2.usecase.userInfo.GetUserInfoUseCase;

import retrofit.RetrofitError;

public class InitialUseActivity extends AppCompatActivity {

    private static final String TAG = InitialUseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_use);
        setToolbar();
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }

        final EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        Button userRegisterButton = (Button) findViewById(R.id.userRegisterButton);
        userRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = userNameEditText.getText().toString();
                if (!userName.isEmpty()) {
                    GetUserInfoUseCase getUserInfoUseCase = new GetUserInfoUseCase();
                    getUserInfoUseCase.apply(userName, new SuccessCallback<RetrofitSuccessEvent<User>>() {
                        @Override
                        public void onSuccess(RetrofitSuccessEvent<User> success) {
                            InitialUseActivity.this.startActivity(MainActivity.createIntent(InitialUseActivity.this));
                        }
                    }, new FailureCallback<RetrofitError>() {
                        @Override
                        public void onFailure(RetrofitError error) {
                            Log.e(TAG, "UseCase : " + GetUserInfoUseCase.class.getSimpleName(), error);
                        }
                    });
                }
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_register);
        setSupportActionBar(toolbar);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context.getApplicationContext(), InitialUseActivity.class);
    }

}
