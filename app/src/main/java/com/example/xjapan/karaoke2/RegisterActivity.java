/*
* 検索結果を表示し曲をDBに登録する画面
* （SearchSangMusicActivity or SearchWantMusicActivityから遷移
* 　SearchSangMusicActivity or SearchWantMusicActivityへと遷移）
*
*  アーティスト名 or 曲名から曲を検索し表示
*
*  表示された曲を選択することでDBに登録
*
* */

package com.example.xjapan.karaoke2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    private ListView registerMusicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        int typeFlag = intent.getIntExtra("typeFlag", 2);
        final int viewFlag = intent.getIntExtra("viewFlag", 2);
        String postName = intent.getStringExtra("postName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        registerMusicListView = (ListView) findViewById(R.id.registerMusicListView);
        if (typeFlag == 0) {
            toolbar.setTitle("アーティスト名(" + postName + ")");
        } else if (typeFlag == 1) {
            toolbar.setTitle("曲名(" + postName + ")");
        } else {
            toolbar.setTitle("error(" + postName + ")");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (viewFlag == 0) {
            AppClient.getService().getSearchMusicTitleByMusicName(postName, 30, 0, new Callback<List<MusicTitle>>() {
                @Override
                public void success(List<MusicTitle> musicTitleList, Response response) {
                    RegisterMusicListAdapter registerMusicListAdapter = new RegisterMusicListAdapter(getApplicationContext(), musicTitleList, viewFlag);
                    registerMusicListView.setAdapter(registerMusicListAdapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("musicRecommendList_test", error.toString());
                }
            });

        } else if (viewFlag == 1) {
            AppClient.getService().getSearchMusicTitleByArtistName(postName, 30, 0, new Callback<List<MusicTitle>>() {
                @Override
                public void success(List<MusicTitle> musicTitleList, Response response) {
                    RegisterMusicListAdapter registerMusicListAdapter = new RegisterMusicListAdapter(getApplicationContext(), musicTitleList, viewFlag);
                    registerMusicListView.setAdapter(registerMusicListAdapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("musicRecommendList_test", error.toString());
                }
            });
        }

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
}
