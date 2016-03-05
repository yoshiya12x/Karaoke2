package com.example.xjapan.karaoke2.usecase.registration;

import com.example.xjapan.karaoke2.db.dao.UserDAO;
import com.example.xjapan.karaoke2.model.MusicTitle;
import com.example.xjapan.karaoke2.model.User;
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

    private final UserDAO dao = UserDAO.get();

    public RegisterMusicUseCase() {
    }

    public void apply(MusicTitle musicTitle, final SuccessCallback<RetrofitSuccessEvent<User>> successCallback, final FailureCallback<RetrofitError> failureCallback) {
        assert successCallback != null;
        assert failureCallback != null;

        User user = dao.findFirst();
        AppClient.getService().register_sung_music(user.accountId, musicTitle.getMusicId(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                successCallback.onSuccess(new RetrofitSuccessEvent<>(user, response));
            }

            @Override
            public void failure(RetrofitError error) {
                failureCallback.onFailure(error);
            }
        });

    }
}
