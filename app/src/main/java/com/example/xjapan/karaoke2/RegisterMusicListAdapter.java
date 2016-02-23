package com.example.xjapan.karaoke2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xjapan on 16/02/23.
 */
public class RegisterMusicListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> musicList;

    public RegisterMusicListAdapter(Context context, ArrayList<String> musicList) {
        this.inflater = LayoutInflater.from(context);
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.search_music_list_item, viewGroup, false);
        TextView musicNameTextView = (TextView) view.findViewById(R.id.sear_music_name);
        musicNameTextView.setText(musicList.get(i));
        return view;
    }
}
