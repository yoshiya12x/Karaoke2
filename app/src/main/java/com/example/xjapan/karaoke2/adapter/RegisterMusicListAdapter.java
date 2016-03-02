package com.example.xjapan.karaoke2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.model.MusicTitle;
import com.example.xjapan.karaoke2.model.UserInfo;
import com.example.xjapan.karaoke2.model.ViewHolder;
import com.example.xjapan.karaoke2.sqlite.UserDB;
import com.example.xjapan.karaoke2.util.AppClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xjapan on 16/02/23.
 */
public class RegisterMusicListAdapter extends BaseAdapter {

    private Context context;
    private UserDB userDB;
    private LayoutInflater inflater;
    private List<MusicTitle> musicTitleList;

    public RegisterMusicListAdapter(Context context, List<MusicTitle> musicTitleList) {
        this.context = context;
        this.userDB = new UserDB(context);
        this.inflater = LayoutInflater.from(context);
        this.musicTitleList = musicTitleList;
    }

    @Override
    public int getCount() {
        return musicTitleList.size();
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
            view = inflater.inflate(R.layout.search_music_list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.musicNameTextView = (TextView) view.findViewById(R.id.sear_music_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String musicName = musicTitleList.get(i).getTitle() + "(" + musicTitleList.get(i).getArtist() + ")";
        holder.musicNameTextView.setText(musicName);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.register_music_linear_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> userInfo = userDB.selectAll();
                AppClient.getService().register_sung_music(Integer.parseInt(userInfo.get(0)), musicTitleList.get(i).getMusicId(), new Callback<UserInfo>() {
                    @Override
                    public void success(UserInfo userInfo, Response response) {
                        Toast.makeText(context, "登録しました", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("musicRecommendList_test", error.toString());
                    }
                });
            }
        });
        return view;
    }
}
