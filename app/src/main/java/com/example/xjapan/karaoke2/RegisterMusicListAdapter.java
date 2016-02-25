package com.example.xjapan.karaoke2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xjapan on 16/02/23.
 */
public class RegisterMusicListAdapter extends BaseAdapter {

    private Context context;
    private UserDB userDB;
    private LayoutInflater inflater;
    private List<MusicTitle> musicTitleList;
    private int viewFlag;
    private LinearLayout linearLayout;

    public RegisterMusicListAdapter(Context context, List<MusicTitle> musicTitleList, int viewFlag) {
        this.context = context;
        this.userDB = new UserDB(context);
        this.inflater = LayoutInflater.from(context);
        this.musicTitleList = musicTitleList;
        this.viewFlag = viewFlag;
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
        view = inflater.inflate(R.layout.search_music_list_item, viewGroup, false);
        TextView musicNameTextView = (TextView) view.findViewById(R.id.sear_music_name);
        musicNameTextView.setText(musicTitleList.get(i).title + "(" + musicTitleList.get(i).artist + ")");
        linearLayout = (LinearLayout) view.findViewById(R.id.register_music_linear_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewFlag == 0) {

                } else if (viewFlag == 1) {
//                    ArrayList<String> userInfo = userDB.selectAll();
//                    AppClient.getService().register_sung_music(Integer.parseInt(userInfo.get(0)), musicTitleList.get(i).music_id, new Callback<UserInfo>() {
//                        @Override
//                        public void success(UserInfo userInfo, Response response) {
//                            Toast.makeText(context, "登録しました", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void failure(RetrofitError error) {
//                            Log.d("musicRecommendList_test", error.toString());
//                        }
//                    });
                }
            }
        });

        return view;
    }
}
