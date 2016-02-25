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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView alertRegisterTextView;
    private ListView registerMusicListView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;

        Intent intent = getIntent();
        int typeFlag = intent.getIntExtra("typeFlag", 2);
        String postName = intent.getStringExtra("postName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alertRegisterTextView = (TextView) findViewById(R.id.alertRegister);
        registerMusicListView = (ListView) findViewById(R.id.registerMusicListView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (typeFlag == 0) {
            toolbar.setTitle("アーティスト名(" + postName + ")");
            setSearchMusicTitleFastByArtistName(postName);
            setSearchMusicTitleByArtistName(postName);
        } else if (typeFlag == 1) {
            toolbar.setTitle("曲名(" + postName + ")");
            setSearchMusicTitleFastByMusicName(postName);
            setSearchMusicTitleByMusicName(postName);
        }
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

    public void setSearchMusicTitleByArtistName(String postName) {
        AppClient.getService().getSearchMusicTitleByArtistName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitleList, Response response) {
                RegisterMusicListAdapter registerMusicListAdapter = new RegisterMusicListAdapter(context, musicTitleList);
                registerMusicListView.setAdapter(registerMusicListAdapter);
                alertRegisterTextView.setText("登録する曲をタップしてください");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    public void setSearchMusicTitleByMusicName(String postName) {
        AppClient.getService().getSearchMusicTitleByMusicName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitleList, Response response) {
                RegisterMusicListAdapter registerMusicListAdapter = new RegisterMusicListAdapter(context, musicTitleList);
                registerMusicListView.setAdapter(registerMusicListAdapter);
                alertRegisterTextView.setText("登録する曲をタップしてください");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    public void setSearchMusicTitleFastByArtistName(String postName) {
        AppClient.getService().getSearchMusicTitleFastByArtistName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitleList, Response response) {
                RegisterMusicListAdapter registerMusicListAdapter = new RegisterMusicListAdapter(context, musicTitleList);
                registerMusicListView.setAdapter(registerMusicListAdapter);
                alertRegisterTextView.setText("登録する曲をタップしてください  人気順に変換中...");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    public void setSearchMusicTitleFastByMusicName(String postName) {
        AppClient.getService().getSearchMusicTitleFastByMusicName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitleList, Response response) {
                RegisterMusicListAdapter registerMusicListAdapter = new RegisterMusicListAdapter(context, musicTitleList);
                registerMusicListView.setAdapter(registerMusicListAdapter);
                alertRegisterTextView.setText("登録する曲をタップしてください  人気順に変換中...");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }
}
