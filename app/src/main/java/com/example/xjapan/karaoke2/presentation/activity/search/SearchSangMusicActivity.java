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

package com.example.xjapan.karaoke2.presentation.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.presentation.activity.MainActivity;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.presentation.activity.registration.RegisterActivity;

public class SearchSangMusicActivity extends AppCompatActivity {

    private EditText artistEditText;
    private EditText musicEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sang_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("歌った曲を登録");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        artistEditText = (EditText) findViewById(R.id.artistSangEditText);
        Button artistSearchButton = (Button) findViewById(R.id.searchArtistSangButton);
        artistSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpannableStringBuilder sb = (SpannableStringBuilder) artistEditText.getText();
                if (!sb.toString().equals("")) {
                    Intent intent = RegisterActivity.createIntent(getApplicationContext(), 0, sb.toString());
                    startActivity(intent);
                }
            }
        });

        musicEditText = (EditText) findViewById(R.id.musicSangEditText);
        Button musicSearchButton = (Button) findViewById(R.id.searchMusicSangButton);
        musicSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpannableStringBuilder sb = (SpannableStringBuilder) musicEditText.getText();
                if (!sb.toString().equals("")) {
                    Intent intent = RegisterActivity.createIntent(getApplicationContext(), 1, sb.toString());
                    startActivity(intent);
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

}
