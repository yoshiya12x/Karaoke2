package com.example.xjapan.karaoke2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.model.MusicTitle;
import com.example.xjapan.karaoke2.model.ViewHolder;

import java.util.List;

/**
 * Created by xjapan on 16/02/23.
 */
public class RegisterMusicListAdapter extends ArrayAdapter<MusicTitle> {

    private LayoutInflater inflater;

    public RegisterMusicListAdapter(Context context, int resourceId, List<MusicTitle> musicTitleList) {
        super(context, resourceId, musicTitleList);
        this.inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.search_music_list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.musicNameTextView = (TextView) view.findViewById(R.id.sear_music_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String musicName = getItem(i).getTitle() + "(" + getItem(i).getArtist() + ")";
        holder.musicNameTextView.setText(musicName);
        return view;
    }
}
