package com.example.xjapan.karaoke2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xjapan on 16/02/23.
 */
public class SuggestionMusicListAdapter extends BaseAdapter {

    private List<MusicRecommend> musicRecommendList;
    private LayoutInflater inflater;

    public SuggestionMusicListAdapter(Context context, List<MusicRecommend> musicRecommendList) {
        this.musicRecommendList = musicRecommendList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return musicRecommendList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.suggestion_music_list_item, viewGroup, false);
        TextView musicNameTextView = (TextView) view.findViewById(R.id.music_name);
        MusicRecommend musicRecommend = musicRecommendList.get(i);
        Log.d("test", musicRecommend.music_id+"");
        musicNameTextView.setText(musicRecommend.title + "(" + musicRecommend.artist + ")");

        return view;
    }
}
