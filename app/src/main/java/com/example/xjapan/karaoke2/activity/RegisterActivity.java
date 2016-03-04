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

package com.example.xjapan.karaoke2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.adapter.RegisterMusicListAdapter;
import com.example.xjapan.karaoke2.model.MusicTitle;
import com.example.xjapan.karaoke2.model.UserInfo;
import com.example.xjapan.karaoke2.sqlite.UserDB;
import com.example.xjapan.karaoke2.usecase.common.FailureCallback;
import com.example.xjapan.karaoke2.usecase.common.RetrofitSuccessEvent;
import com.example.xjapan.karaoke2.usecase.common.SuccessCallback;
import com.example.xjapan.karaoke2.usecase.registration.RegisterMusicUseCase;
import com.example.xjapan.karaoke2.util.AppClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView alertRegisterTextView;
    private ListView registerMusicListView;
    private Context context;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        Intent intent = getIntent();
        int typeFlag = intent.getIntExtra("typeFlag", 2);
        String postName = intent.getStringExtra("postName");
        Toolbar toolbar = setToolBar();
        alertRegisterTextView = (TextView) findViewById(R.id.alertRegister);
        registerMusicListView = (ListView) findViewById(R.id.registerMusicListView);
        if (typeFlag == 0) {
            toolbar.setTitle("アーティスト名(" + postName + ")");
            setSearchMusicTitleFastByArtistName(postName);
            setSearchMusicTitleByArtistName(postName);
        } else if (typeFlag == 1) {
            toolbar.setTitle("曲名(" + postName + ")");
            setSearchMusicTitleFastByMusicName(postName);
            setSearchMusicTitleByMusicName(postName);
        }
        registerMusicListView.setOnItemClickListener(createOnItemClickListener());
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

    private Toolbar setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return toolbar;
    }

    private void setSearchMusicTitleByArtistName(String postName) {
        AppClient.getService().getSearchMusicTitleByArtistName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitleList, Response response) {
                setView(musicTitleList, R.string.text_tap_song);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    private void setSearchMusicTitleByMusicName(String postName) {
        AppClient.getService().getSearchMusicTitleByMusicName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitleList, Response response) {
                setView(musicTitleList, R.string.text_tap_song);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    private void setSearchMusicTitleFastByArtistName(String postName) {
        AppClient.getService().getSearchMusicTitleFastByArtistName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitleList, Response response) {
                setView(musicTitleList, R.string.text_tap_song_changing);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    private void setSearchMusicTitleFastByMusicName(String postName) {
        AppClient.getService().getSearchMusicTitleFastByMusicName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitleList, Response response) {
                setView(musicTitleList, R.string.text_tap_song_changing);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    private void setView(List<MusicTitle> musicTitleList, int alertText) {
        RegisterMusicListAdapter registerMusicListAdapter = new RegisterMusicListAdapter(context, R.id.registerMusicListView, musicTitleList);
        registerMusicListView.setAdapter(registerMusicListAdapter);
        alertRegisterTextView.setText(alertText);
    }

    public static Intent createIntent(Context context, int typeFlag, String postName) {
        Intent intent = new Intent(context.getApplicationContext(), RegisterActivity.class);
        intent.putExtra("typeFlag", typeFlag);
        intent.putExtra("postName", postName);
        return intent;
    }

    private AdapterView.OnItemClickListener createOnItemClickListener() {
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                ListView listView = (ListView) adapterView;
                MusicTitle musicTitle = (MusicTitle) listView.getItemAtPosition(i);
                UserDB userDB = new UserDB(RegisterActivity.this);
                RegisterMusicUseCase useCase = new RegisterMusicUseCase(userDB);
                useCase.apply(musicTitle, new SuccessCallback<RetrofitSuccessEvent<UserInfo>>() {
                    @Override
                    public void onSuccess(RetrofitSuccessEvent<UserInfo> success) {
                        Snackbar.make(view, R.string.toast_register, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }, new FailureCallback<RetrofitError>() {
                    @Override
                    public void onFailure(RetrofitError error) {
                        Log.e(TAG, "UseCase : " + RegisterMusicUseCase.class.getSimpleName(), error);
                    }
                });
            }
        };
        return onItemClickListener;
    }

}
