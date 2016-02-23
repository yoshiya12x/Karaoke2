package com.example.xjapan.karaoke2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RegisterMusicListAdapter extends BaseAdapter {

    private ArrayList<String> musicList;
    private LayoutInflater inflater;

    public RegisterMusicListAdapter(Context context, ArrayList<String> musicList){
        this.musicList = musicList;
        this.inflater = LayoutInflater.from(context);
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
        view = inflater.inflate(R.layout.register_music_list_item,viewGroup,false);
        TextView musicNameTextView = (TextView) view.findViewById(R.id.music_name);
        musicNameTextView.setText(musicList.get(i));

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.registerMusicList);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
