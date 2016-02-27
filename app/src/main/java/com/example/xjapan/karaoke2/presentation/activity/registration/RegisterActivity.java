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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xjapan.karaoke2.domain.value.Paging;
import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.presentation.activity.BaseActivity;
import com.example.xjapan.karaoke2.presentation.activity.main.MainActivity;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.presentation.activity.search.SearchSangMusicActivity;
import com.example.xjapan.karaoke2.presentation.activity.search.SearchType;
import com.example.xjapan.karaoke2.presentation.common.adapter.ArrayRecyclerAdapter;
import com.example.xjapan.karaoke2.presentation.common.decoration.SpaceItemDecoration;
import com.example.xjapan.karaoke2.usecase.registration.FetchMusicTitlesByArtistNameUseCase;
import com.example.xjapan.karaoke2.usecase.registration.FetchMusicTitlesByMusicNameUseCase;
import com.example.xjapan.karaoke2.usecase.registration.FetchMusicTitlesUseCase;
import com.example.xjapan.karaoke2.usecase.registration.RegisterMusicUseCase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private static final String KEY_EXTRA_TYPE = TAG + "type";
    private static final String KEY_EXTRA_NAME = TAG + "name";

    private static final Paging INITIAL_PAGE = new Paging(30);

    @Bind(R.id.alertRegister)
    TextView alertRegisterTextView;

    private Paging paging = INITIAL_PAGE;
    private MusicAdapter adapter;

    public static Intent createIntent(@NonNull Context context, SearchType type, @NonNull String name) {
        Intent intent = new Intent(context.getApplicationContext(), RegisterActivity.class);
        intent.putExtra(KEY_EXTRA_TYPE, type);
        intent.putExtra(KEY_EXTRA_NAME, name);
        return intent;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        SearchType type = (SearchType) intent.getSerializableExtra(KEY_EXTRA_TYPE);
        String postName = intent.getStringExtra(KEY_EXTRA_NAME);

        assert type != null;
        assert postName != null;

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView recyclerView = ButterKnife.findById(this, R.id.recycler_view);
        recyclerView.setAdapter(adapter = new MusicAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int space = getResources().getDimensionPixelSize(R.dimen.margin_between_cards);
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));

        toolbar.setTitle(getString(type.getId(), postName));
        EventBus.getDefault().post(type.getEvent(postName));
    }

    @Subscribe
    public void onEvent(SearchType.MusicTriggerEvent event) {
        new FetchMusicTitlesByMusicNameUseCase().apply(event.getQuery(), paging);
        paging = paging.next();
    }

    @Subscribe
    public void onEvent(SearchType.ArtistTriggerEvent event) {
        new FetchMusicTitlesByArtistNameUseCase().apply(event.getQuery(), paging);
        paging = paging.next();
    }

    @Subscribe
    public void onEvent(FetchMusicTitlesUseCase.OnFetchedMusicTitlesEvent event) {
        adapter.addAll(event.getMusics());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                alertRegisterTextView.setText(R.string.text__register__tap_a_music);
            }
        });
    }

    @Subscribe
    public void onEvent(FetchMusicTitlesUseCase.OnFetchMusicTitlesFailureEvent event) {
        switch (event.getKind()) {
            case Network:
                showErrorSnack(alertRegisterTextView, R.string.error__usecase__fetch_music_titles__network);
                break;
            case Unknown:
                showErrorSnack(alertRegisterTextView, R.string.error__usecase__unknown);
                break;
            default:
                throw new AssertionError("No consistency in OnFetchMusicTitlesFailureEvent");
        }
    }

    @Subscribe
    public void onEvent(RegisterMusicUseCase.OnCreatedSungMusicEvent event) {
        Snackbar.make(alertRegisterTextView, R.string.text__register__register_done, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Subscribe
    public void onEvent(RegisterMusicUseCase.OnCreateSungMusicFailureEvent event) {
        switch (event.getKind()) {
            case Network:
                showErrorSnack(alertRegisterTextView, R.string.error__usecase__create_sung_music__network);
                break;
            case DatabaseIO:
                showErrorSnack(alertRegisterTextView, R.string.error__usecase__create_sung_music__database_io);
                break;
            default:
                throw new AssertionError("No consistency in OnCreateSungMusicFailureEvent");
        }
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.card_view)
        CardView cardView;

        @Bind(R.id.text_music_name)
        TextView musicNameText;

        @Bind(R.id.text_artist_name)
        TextView artistNameText;

        MusicTitle music;

        public MusicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            assert music != null;

            new RegisterMusicUseCase().apply(music);
        }
    }

    private class MusicAdapter extends ArrayRecyclerAdapter<MusicTitle, MusicViewHolder> {

        public MusicAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MusicViewHolder(LayoutInflater.from(RegisterActivity.this).inflate(R.layout.row_music, parent, false));
        }

        @Override
        public void onBindViewHolder(MusicViewHolder holder, int position) {
            MusicTitle music = get(position);

            holder.music = music;
            holder.musicNameText.setText(music.getTitle());
            holder.artistNameText.setText(getString(R.string.format_parentheses_wrap, music.getArtist()));
            holder.cardView.setOnClickListener(holder);
        }
    }
}
