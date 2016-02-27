package com.example.xjapan.karaoke2.usecase.registration;

import android.util.Log;

import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;
import com.example.xjapan.karaoke2.infra.db.dao.UserDB;
import com.example.xjapan.karaoke2.infra.db.entity.UserInfo;
import com.example.xjapan.karaoke2.usecase.common.FailureCallback;
import com.example.xjapan.karaoke2.usecase.common.RetrofitSuccessEvent;
import com.example.xjapan.karaoke2.usecase.common.SuccessCallback;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class RegisterMusicUseCase {
    private final UserDB dao;

    public RegisterMusicUseCase(UserDB dao) {
        this.dao = dao;
    }

    public void apply(MusicTitle music, final SuccessCallback<RetrofitSuccessEvent<UserInfo>> sucCb, final FailureCallback<RetrofitError> errCb) {
        assert sucCb != null;
        assert errCb != null;

        int userId = Integer.parseInt(dao.selectAll().get(0));

        AppClient.getService().register_sung_music(userId, music.getMusicId(), new Callback<UserInfo>() {
            @Override
            public void success(UserInfo userInfo, Response response) {
                sucCb.onSuccess(new RetrofitSuccessEvent<>(userInfo, response));
//                Toast.makeText(context, "登録しました", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                errCb.onFailure(error);
            }
        });
    }
}
