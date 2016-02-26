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

package com.example.xjapan.karaoke2.presentation.activity.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.infra.db.dao.UserDB;
import com.example.xjapan.karaoke2.infra.db.entity.UserInfo;
import com.example.xjapan.karaoke2.presentation.activity.MainActivity;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.presentation.activity.search.SearchSangMusicActivity;
import com.example.xjapan.karaoke2.presentation.common.adapter.ArrayRecyclerAdapter;
import com.example.xjapan.karaoke2.presentation.common.decoration.SpaceItemDecoration;
import com.example.xjapan.karaoke2.usecase.common.FailureCallback;
import com.example.xjapan.karaoke2.usecase.common.RetrofitSuccessEvent;
import com.example.xjapan.karaoke2.usecase.common.SuccessCallback;
import com.example.xjapan.karaoke2.usecase.registration.RegisterMusicUseCase;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private TextView alertRegisterTextView;
    private MusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        int typeFlag = intent.getIntExtra("typeFlag", 2);
        String postName = intent.getStringExtra("postName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        alertRegisterTextView = (TextView) findViewById(R.id.alertRegister);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.registerMusicListView);
        recyclerView.setAdapter(adapter = new MusicAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int space = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));


        if (typeFlag == 0) {
            toolbar.setTitle(getString(R.string.title__register__format__artist, postName));
            setSearchMusicTitleFastByArtistName(postName);
            setSearchMusicTitleByArtistName(postName);
        } else if (typeFlag == 1) {
            toolbar.setTitle(getString(R.string.title__register__format__music, postName));
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
                adapter.addAllWithNotify(musicTitleList);

                alertRegisterTextView.setText(R.string.text__register__tap_a_music);
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
                adapter.addAllWithNotify(musicTitleList);
                alertRegisterTextView.setText(R.string.text__register__tap_a_music);
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
                adapter.addAllWithNotify(musicTitleList);
                alertRegisterTextView.setText(R.string.text__register__tap_a_music_with_popular_order);
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
                adapter.addAllWithNotify(musicTitleList);
                alertRegisterTextView.setText(R.string.text__register__tap_a_music_with_popular_order);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    private class MusicViewHolder extends RecyclerView.ViewHolder {
        private TextView musicNameText;
        private View body;

        public MusicViewHolder(View itemView) {
            super(itemView);

            body = itemView.findViewById(R.id.register_music_linear_layout);
            musicNameText = (TextView) itemView.findViewById(R.id.sear_music_name);

            assert body != null;
            assert musicNameText != null;
        }
    }

    private class MusicAdapter extends ArrayRecyclerAdapter<MusicTitle, MusicViewHolder> {

        public MusicAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MusicViewHolder(LayoutInflater.from(RegisterActivity.this).inflate(R.layout.search_music_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MusicViewHolder holder, int position) {
            final MusicTitle music = get(position);

            String name = getString(R.string.format_music_info, music.getTitle(), music.getArtist());
            holder.musicNameText.setText(name);

            holder.body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    RegisterMusicUseCase useCase = new RegisterMusicUseCase(new UserDB(getApplicationContext()));
                    useCase.apply(music, new SuccessCallback<RetrofitSuccessEvent<UserInfo>>() {
                        @Override
                        public void onSuccess(RetrofitSuccessEvent<UserInfo> success) {
                            Snackbar.make(v, R.string.text__register__register_done, Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }, new FailureCallback<RetrofitError>() {
                        @Override
                        public void onFailure(RetrofitError error) {
                            Log.e(TAG, "UseCase : " + RegisterMusicUseCase.class.getSimpleName(), error);
                        }
                    });
                }
            });
        }
    }
}
