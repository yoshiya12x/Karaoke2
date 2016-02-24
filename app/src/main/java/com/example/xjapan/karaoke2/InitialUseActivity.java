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

package com.example.xjapan.karaoke2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InitialUseActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private Button userRegisterButton;
    private SpannableStringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_use);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登録画面");
        setSupportActionBar(toolbar);

        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        sb = (SpannableStringBuilder) userNameEditText.getText();

        userRegisterButton = (Button) findViewById(R.id.userRegisterButton);
        userRegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!sb.toString().equals("")) {
                    UserDB userDB = new UserDB(view.getContext());
                    userDB.insertAll(sb.toString());
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);
                }

            }
        });
    }

}
