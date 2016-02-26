package com.example.xjapan.karaoke2.presentation.activity.suggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.ViewHolder;
import com.example.xjapan.karaoke2.infra.api.entity.MusicRecommend;

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
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.suggestion_music_list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.musicNameTextView = (TextView) view.findViewById(R.id.music_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MusicRecommend musicRecommend = musicRecommendList.get(i);
        String musicName = musicRecommend.getTitle() + "(" + musicRecommend.getArtist() + ")";
        holder.musicNameTextView.setText(musicName);
        return view;
    }
}
