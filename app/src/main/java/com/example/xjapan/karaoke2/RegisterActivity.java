package com.example.xjapan.karaoke2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private int typeFlag = 0;
    private int viewFlag = 0;
    private String postName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        typeFlag = intent.getIntExtra("typeFlag", 2); //0:artist 1:music 2:error
        viewFlag = intent.getIntExtra("viewFlag", 2); //0:want 1:sang 2:error
        postName = intent.getStringExtra("postName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("検索結果(" + postName + ")");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ListView dayListView = (ListView) findViewById(R.id.registerMusicListView);
        RegisterMusicListAdapter registerMusicListAdapter = new RegisterMusicListAdapter(RegisterActivity.this, getMusicList());
        dayListView.setAdapter(registerMusicListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public ArrayList<String> getMusicList() {
        ArrayList<String> musicList = new ArrayList<>();
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
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
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
