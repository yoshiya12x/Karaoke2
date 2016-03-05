package com.example.xjapan.karaoke2.usecase.userInfo;

import com.example.xjapan.karaoke2.db.dao.UserDAO;
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
public class GetUserInfoUseCase {

    private final UserDAO dao = UserDAO.get();

    public GetUserInfoUseCase() {
    }

    public void apply(String userName, final SuccessCallback<RetrofitSuccessEvent<User>> successCallback, final FailureCallback<RetrofitError> failureCallback) {
        assert successCallback != null;
        assert failureCallback != null;

        AppClient.getService().getUserInfo(userName, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                long id = dao.insert(user);
                if (id > 0) {
                    successCallback.onSuccess(new RetrofitSuccessEvent<>(user, response));
                } else {

                }
            }

            @Override
            public void failure(RetrofitError error) {
                failureCallback.onFailure(error);
            }
        });
    }
}
