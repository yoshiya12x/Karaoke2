package com.example.xjapan.karaoke2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SuggestionActivity extends AppCompatActivity {

    private ArrayList<String> musicList;
    private ListView musicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("roomName"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        musicListView = (ListView) findViewById(R.id.suggetionMusicListView);

        musicList = new ArrayList<>();
        AppClient.getService().getMusicRecommend(1, new Callback<List<MusicRecommend>>() {
            @Override
            public void success(List<MusicRecommend> musicRecommendList, Response response) {
                for (int i = 0; i < musicRecommendList.size(); i++) {
                    musicList.add(musicRecommendList.get(i).title + "(" + musicRecommendList.get(i).artist + ")");
                }
                SuggestionMusicListAdapter suggestionMusicListAdapter = new SuggestionMusicListAdapter(getApplicationContext(), musicList);
                musicListView.setAdapter(suggestionMusicListAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
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
