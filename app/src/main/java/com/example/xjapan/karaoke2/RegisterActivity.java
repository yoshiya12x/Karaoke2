package com.example.xjapan.karaoke2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private int typeFlag = 0;
    private int viewFlag = 0;
    private String artistName = null;
    private String musicName = null;

    private String selectMusicName = null;

    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登録");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        typeFlag = intent.getIntExtra("typeFlag", 2); //0:artist 1:music 2:error
        viewFlag = intent.getIntExtra("viewFlag", 2); //0:want 1:sang 2:error
        artistName = intent.getStringExtra("artistName");
        musicName = intent.getStringExtra("musicName");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        registerButton = (Button) findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    registerButton.setText(selectMusicName);
//                Intent intent = new Intent(view.getContext(), SuggestionActivity.class);
//                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView dayListView = (ListView) findViewById(R.id.registerMusicListView);
        SuggestionMusicListAdapter suggestionMusicListAdapter = new SuggestionMusicListAdapter(RegisterActivity.this, getMusicList());
        dayListView.setAdapter(suggestionMusicListAdapter);
    }

    public ArrayList<String> getMusicList() {
        ArrayList<String> musicList = new ArrayList<>();
        //DBから取得
        //これはテスト
        for (int i = 0; i < 200; i++) {
            musicList.add(i + "番目の曲");
        }
        return musicList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_want_music) {
            Intent intent = new Intent(this, SearchWantMusicActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.action_sang_music) {
            Intent intent = new Intent(this, SearchSangMusicActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.action_logout) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
