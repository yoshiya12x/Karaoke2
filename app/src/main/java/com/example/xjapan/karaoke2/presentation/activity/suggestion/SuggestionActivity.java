package com.example.xjapan.karaoke2.presentation.activity.suggestion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.domain.entity.User;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;
import com.example.xjapan.karaoke2.presentation.activity.BaseActivity;
import com.example.xjapan.karaoke2.infra.api.entity.MusicRecommend;
import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.presentation.common.adapter.ArrayRecyclerAdapter;
import com.example.xjapan.karaoke2.presentation.common.decoration.SpaceItemDecoration;
import com.example.xjapan.karaoke2.usecase.suggestion.FetchMusicRecommendationsUseCase;
import com.example.xjapan.karaoke2.usecase.suggestion.LeaveRoomUseCase;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SuggestionActivity extends BaseActivity {

    private static final String TAG = SuggestionActivity.class.getSimpleName();
    private static final String KEY_ROOM_NAME = TAG + ".roomName";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MusicAdapter adapter;

    public static Intent createIntent(@NonNull Context context, @NonNull String roomName) {
        Intent intent = new Intent(context.getApplicationContext(), SuggestionActivity.class);

        intent.putExtra(KEY_ROOM_NAME, roomName);

        return intent;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_suggestion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        toolbar.setTitle(intent.getStringExtra(KEY_ROOM_NAME));
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = ButterKnife.findById(this, R.id.recycler_view);
        recyclerView.setAdapter(adapter = new MusicAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int space = getResources().getDimensionPixelSize(R.dimen.margin_between_cards);
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));

        new FetchMusicRecommendationsUseCase().apply();
    }

    public void reload() {
        adapter.clear();
        adapter.notifyDataSetChanged();

        new FetchMusicRecommendationsUseCase().apply();
    }

    public void leave() {
        new LeaveRoomUseCase().apply();
    }

    @Subscribe
    public void onEvent(FetchMusicRecommendationsUseCase.OnFetchedMusicRecommendationsEvent event) {
        adapter.addAll(event.getRecommendations());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Subscribe
    public void onEvent(FetchMusicRecommendationsUseCase.OnFetchMusicRecommendationsFailureEvent event) {
        switch (event.getKind()) {
            case Network:
                showErrorSnack(toolbar, R.string.error__usecase__fetch_music_recommendations__network);
                break;
            case DatabaseIO:
                showErrorSnack(toolbar, R.string.error__usecase__fetch_music_recommendations__database_io);
                break;
            case Unknown:
                showErrorSnack(toolbar, R.string.error__usecase__unknown);
                break;
            default:
                throw new AssertionError("No consistency in OnCreateUserFailureEvent");
        }
    }

    @Subscribe
    public void onEvent(LeaveRoomUseCase.LeavedRoomEvent event) {
        finish();
    }

    @Subscribe
    public void onEvent(LeaveRoomUseCase.LeaveRoomFailureEvent event) {
        switch (event.getKind()) {
            case Network:
                showErrorSnack(toolbar, R.string.error__usecase__fetch_music_recommendations__network);
                break;
            case DatabaseIO:
                showErrorSnack(toolbar, R.string.error__usecase__fetch_music_recommendations__database_io);
                break;
            case Unknown:
                showErrorSnack(toolbar, R.string.error__usecase__unknown);
                break;
            default:
                throw new AssertionError("No consistency in OnCreateUserFailureEvent");
        }
    }

    @Override
    public void onBackPressed() {
        SuggestionMenu.Leave.doAction(this);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SuggestionMenu menu = SuggestionMenu.fromId(item.getItemId());

        return menu != null && menu.doAction(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void invokeRoomOut() {
        AppClient.getService().roomOut(0, new Callback<User>() {
            @Override
            public void success(User successUserInfo, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("musicRecommendList_test", error.toString());
            }
        });
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_music_name)
        TextView musicNameText;

        @Bind(R.id.text_artist_name)
        TextView artistNameText;

        public MusicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class MusicAdapter extends ArrayRecyclerAdapter<MusicRecommend, MusicViewHolder> {

        public MusicAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MusicViewHolder(LayoutInflater.from(SuggestionActivity.this).inflate(R.layout.row_music, parent, false));
        }

        @Override
        public void onBindViewHolder(MusicViewHolder holder, int position) {
            final MusicRecommend music = get(position);

            holder.musicNameText.setText(music.getTitle());
            holder.artistNameText.setText(getString(R.string.format_parentheses_wrap, music.getArtist()));
        }
    }
}
