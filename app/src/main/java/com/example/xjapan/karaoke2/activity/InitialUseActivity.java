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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.util.AppClient;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.sqlite.UserDB;
import com.example.xjapan.karaoke2.model.UserInfo;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InitialUseActivity extends AppCompatActivity {

    private SpannableStringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_use);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登録画面");
        setSupportActionBar(toolbar);
        final EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        Button userRegisterButton = (Button) findViewById(R.id.userRegisterButton);
        userRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb = (SpannableStringBuilder) userNameEditText.getText();
                if (!sb.toString().equals("")) {
                    AppClient.getService().getUserInfo(sb.toString(), new Callback<UserInfo>() {
                        @Override
                        public void success(UserInfo userInfo, Response response) {
                            UserDB userDB = new UserDB(getApplicationContext());
                            userDB.insertAll(userInfo.getAccountId(), sb.toString());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("getUserInfo", error.toString());
                        }
                    });
                }
            }
        });
    }

}
