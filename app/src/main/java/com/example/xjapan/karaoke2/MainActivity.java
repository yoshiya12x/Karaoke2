/*
* アプリ起動時（ユーザ登録後）の画面
*
*
*
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SpannableStringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        UserDB userDB = new UserDB(this);
        ArrayList<String> userInfo = userDB.selectAll();
        if (userInfo.size() == 0) {
            Intent intent = new Intent(this, InitialUseActivity.class);
            this.startActivity(intent);
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
                if (sb.toString().equals("")) {

                } else {
                    Intent intent = new Intent(view.getContext(), SuggestionActivity.class);
                    intent.putExtra("roomName", sb.toString());
                    view.getContext().startActivity(intent);
                }

            }
        });
        Button roomCreateButton = (Button) findViewById(R.id.roomCreateButton);
        roomCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sb.toString().equals("")) {

                } else {
                    Intent intent = new Intent(view.getContext(), SuggestionActivity.class);
                    intent.putExtra("roomName", sb.toString());
                    view.getContext().startActivity(intent);
                }

            }
        });

    }

}
