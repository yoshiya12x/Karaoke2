package com.example.xjapan.karaoke2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.model.MusicRecommend;
import com.example.xjapan.karaoke2.model.ViewHolder;

import java.util.List;

/**
 * Created by xjapan on 16/02/23.
 */
public class SuggestionMusicListAdapter extends ArrayAdapter<MusicRecommend> {

    private LayoutInflater inflater;

    public SuggestionMusicListAdapter(Context context, int resourceId, List<MusicRecommend> musicRecommendList) {
        super(context, resourceId, musicRecommendList);
        this.inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.suggestion_music_list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.musicNameTextView = (TextView) view.findViewById(R.id.music_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MusicRecommend musicRecommend = getItem(i);
        String musicName = musicRecommend.getTitle() + "(" + musicRecommend.getArtist() + ")";
        holder.musicNameTextView.setText(musicName);
        return view;
    }
}
