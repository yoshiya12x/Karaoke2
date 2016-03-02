package com.example.xjapan.karaoke2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.adapter.SuggestionMusicListAdapter;
import com.example.xjapan.karaoke2.model.MusicRecommend;
import com.example.xjapan.karaoke2.model.UserInfo;
import com.example.xjapan.karaoke2.util.AppClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SuggestionActivity extends AppCompatActivity {

    private ListView musicListView;
    private int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        Intent intent = getIntent();
        accountId = intent.getIntExtra("account_id", 0);
        setToolbar(intent.getStringExtra("roomName"));

        musicListView = (ListView) findViewById(R.id.suggetionMusicListView);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMusicRecommend();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sang_music) {
            Intent intent = new Intent(this, SearchSangMusicActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.action_logout) {
            invokeRoomOut();
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_reload) {
            setMusicRecommend();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setToolbar(String roomName) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(roomName);
        setSupportActionBar(toolbar);
    }

    public void setMusicRecommend() {
        AppClient.getService().getMusicRecommend(accountId, new Callback<List<MusicRecommend>>() {
            @Override
            public void success(List<MusicRecommend> musicRecommendList, Response response) {
                SuggestionMusicListAdapter suggestionMusicListAdapter = new SuggestionMusicListAdapter(getApplicationContext(), R.id.suggetionMusicListView, musicRecommendList);
                musicListView.setAdapter(suggestionMusicListAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    public void invokeRoomOut() {
        AppClient.getService().roomOut(accountId, new Callback<UserInfo>() {
            @Override
            public void success(UserInfo successUserInfo, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }
}
