package com.example.xjapan.karaoke2.usecase.userInfo;

import com.example.xjapan.karaoke2.model.UserInfo;
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

    public GetUserInfoUseCase() {
    }

    public void apply(String userName, final SuccessCallback<RetrofitSuccessEvent<UserInfo>> successCallback, final FailureCallback<RetrofitError> failureCallback) {
        assert successCallback != null;
        assert failureCallback != null;

        AppClient.getService().getUserInfo(userName, new Callback<UserInfo>() {
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
