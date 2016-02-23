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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_use);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登録画面");
        setSupportActionBar(toolbar);

        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        userRegisterButton = (Button) findViewById(R.id.userRegisterButton);
        userRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpannableStringBuilder sb = (SpannableStringBuilder) userNameEditText.getText();
                UserDB userDB = new UserDB(view.getContext());
                userDB.insertAll(sb.toString());
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

}
