package com.example.xjapan.karaoke2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button testButton;

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
        }else{
           // toolbar.setTitle(userInfo.get(2));
        }
        setSupportActionBar(toolbar);

        testButton = (Button) findViewById(R.id.roomInButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SuggestionActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

}
