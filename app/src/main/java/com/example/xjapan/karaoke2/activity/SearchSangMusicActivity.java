/*
* 歌った曲を検索するための画面
* （SuggestionActivityから遷移
* 　MainActivity or RegisterActivityへと遷移）
*
* アーティスト名 or 曲名を入力して検索
* andで検索は不可
*
* 入力されていない場合はアラート
*
* */

package com.example.xjapan.karaoke2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.R;

public class SearchSangMusicActivity extends AppCompatActivity {

    private EditText artistEditText;
    private EditText musicEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sang_music);
        setToolbar();

        artistEditText = (EditText) findViewById(R.id.artistSangEditText);
        Button artistSearchButton = (Button) findViewById(R.id.searchArtistSangButton);
        artistSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artistName = artistEditText.getText().toString();
                if (!artistName.isEmpty()) {
                    SearchSangMusicActivity.this.startActivity(RegisterActivity.createIntent(SearchSangMusicActivity.this, 0, artistName));
                }
            }
        });

        musicEditText = (EditText) findViewById(R.id.musicSangEditText);
        Button musicSearchButton = (Button) findViewById(R.id.searchMusicSangButton);
        musicSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String musicName = musicEditText.getText().toString();
                if (!musicName.isEmpty()) {
                    SearchSangMusicActivity.this.startActivity(RegisterActivity.createIntent(SearchSangMusicActivity.this, 1, musicName));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sang_music) {
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

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("歌った曲を登録");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), SearchSangMusicActivity.class);
        return intent;
    }
}
