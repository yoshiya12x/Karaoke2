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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjapan.karaoke2.presentation.activity.main.MainActivity;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.presentation.activity.registration.RegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchSangMusicActivity extends AppCompatActivity {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context.getApplicationContext(), SearchSangMusicActivity.class);
    }

    @Bind(R.id.artistSangEditText)
    EditText artistEditText;

    @Bind(R.id.musicSangEditText)
    EditText musicEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sang_music);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("歌った曲を登録");
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button artistSearchButton = ButterKnife.findById(this, R.id.searchArtistSangButton);
        artistSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = artistEditText.getText().toString();
                if (!TextUtils.isEmpty(query)) {
                    Intent intent = RegisterActivity.createIntent(getApplicationContext(), SearchType.ARTIST, query);
                    startActivity(intent);
                }
            }
        });

        Button musicSearchButton = ButterKnife.findById(this, R.id.searchMusicSangButton);
        musicSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = musicEditText.getText().toString();
                if (!TextUtils.isEmpty(query)) {
                    Intent intent = RegisterActivity.createIntent(getApplicationContext(), SearchType.MUSIC, query);
                    startActivity(intent);
                }
            }
        });
    }

}
