package com.example.xjapan.karaoke2.usecase.registration;

import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;
import com.example.xjapan.karaoke2.domain.dao.UserDAO;
import com.example.xjapan.karaoke2.domain.entity.User;
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

    private final UserDAO dao = UserDAO.get();

    public void applyAsync(MusicTitle music,
                      final SuccessCallback<RetrofitSuccessEvent<User>> sucCb,
                      final FailureCallback<RetrofitError> errCb) {
        assert sucCb != null;
        assert errCb != null;

        User user = dao.findFirst();

        assert user != null;

        int userId = user.accountId;

        AppClient.getService().register_sung_music(userId, music.getMusicId(), new Callback<User>() {
            @Override
            public void success(User userInfo, Response response) {
                sucCb.onSuccess(new RetrofitSuccessEvent<>(userInfo, response));
            }

            @Override
            public void failure(RetrofitError error) {
                errCb.onFailure(error);
            }
        });
    }
}
