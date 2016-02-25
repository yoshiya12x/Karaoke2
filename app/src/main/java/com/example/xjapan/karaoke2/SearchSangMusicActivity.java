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

package com.example.xjapan.karaoke2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchSangMusicActivity extends AppCompatActivity {

    private Button artistSearchButton;
    private Button musicSearchButton;
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
        artistSearchButton = (Button) findViewById(R.id.searchArtistSangButton);
        artistSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpannableStringBuilder sb = (SpannableStringBuilder) artistEditText.getText();
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                //artist=0,music=1
                intent.putExtra("typeFlag", 0);
                //want=0,sang=1
                intent.putExtra("viewFlag", 1);
                intent.putExtra("postName", sb.toString());
                view.getContext().startActivity(intent);
            }
        });

        musicEditText = (EditText) findViewById(R.id.musicSangEditText);
        musicSearchButton = (Button) findViewById(R.id.searchMusicSangButton);
        musicSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpannableStringBuilder sb = (SpannableStringBuilder) musicEditText.getText();
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                //artist=0,music=1
                intent.putExtra("typeFlag", 1);
                //want=0,sang=1
                intent.putExtra("viewFlag", 1);
                intent.putExtra("postName", sb.toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_want_music) {
//            Intent intent = new Intent(this, SearchWantMusicActivity.class);
//            this.startActivity(intent);
//            return true;} else
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
}
