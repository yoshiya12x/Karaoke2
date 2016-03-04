package com.example.xjapan.karaoke2.usecase.registration;

import com.example.xjapan.karaoke2.model.MusicTitle;
import com.example.xjapan.karaoke2.model.UserInfo;
import com.example.xjapan.karaoke2.sqlite.UserDB;
import com.example.xjapan.karaoke2.usecase.common.FailureCallback;
import com.example.xjapan.karaoke2.usecase.common.RetrofitSuccessEvent;
import com.example.xjapan.karaoke2.usecase.common.SuccessCallback;
import com.example.xjapan.karaoke2.util.AppClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xjapan on 16/03/04.
 */
public class RegisterMusicUseCase {

    private final UserDB dao;

    public RegisterMusicUseCase(UserDB dao){
        this.dao = dao;
    }

    public void apply(MusicTitle musicTitle, final SuccessCallback<RetrofitSuccessEvent<UserInfo>> successCallback, final FailureCallback<RetrofitError> failureCallback){
        assert successCallback != null;
        assert failureCallback != null;

        int userId = Integer.parseInt(dao.selectAll().get(0));
        AppClient.getService().register_sung_music(userId, musicTitle.getMusicId(), new Callback<UserInfo>() {
            @Override
            public void success(UserInfo userInfo, Response response) {
                successCallback.onSuccess(new RetrofitSuccessEvent<>(userInfo, response));
            }

            @Override
            public void failure(RetrofitError error) {
                failureCallback.onFailure(error);
            }
        });

    }
}
