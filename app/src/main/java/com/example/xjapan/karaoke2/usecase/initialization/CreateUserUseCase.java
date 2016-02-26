package com.example.xjapan.karaoke2.usecase.initialization;

import com.example.xjapan.karaoke2.infra.api.AppClient;
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
public class CreateUserUseCase {
    private final UserDAO dao = UserDAO.get();

    public void applyAsync(String name,
                      final SuccessCallback<RetrofitSuccessEvent<User>> sucCb,
                      final FailureCallback<RetrofitError> errCb) {
        AppClient.getService().getUserInfo(name, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                long id = dao.insert(user);

                if (id > 0) {
                    sucCb.onSuccess(new RetrofitSuccessEvent<>(user, response));
                } else {
                    // failure handle => EventBus // FIXME
                }
            }

            @Override
            public void failure(RetrofitError error) {
                errCb.onFailure(error);
            }
        });
    }
}
