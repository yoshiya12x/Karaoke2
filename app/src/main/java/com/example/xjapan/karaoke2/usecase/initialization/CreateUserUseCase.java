package com.example.xjapan.karaoke2.usecase.initialization;

import com.example.xjapan.karaoke2.domain.event.ErrorEvent;
import com.example.xjapan.karaoke2.domain.value.ErrorKind;
import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.domain.dao.UserDAO;
import com.example.xjapan.karaoke2.domain.entity.User;
import com.example.xjapan.karaoke2.usecase.common.FailureCallback;
import com.example.xjapan.karaoke2.usecase.common.RetrofitSuccessEvent;
import com.example.xjapan.karaoke2.usecase.common.SuccessCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class CreateUserUseCase {
    private final UserDAO dao = UserDAO.get();
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    public void apply(final String name) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    User user = AppClient.sync().createUser(name);
                    long id = dao.insert(user);

                    if (id > 0) {
                        EventBus.getDefault().post(new OnCreatedUserEvent(user));
                    } else {
                        EventBus.getDefault().post(new OnCreateUserFailureEvent(ErrorKind.DatabaseIO));
                    }
                } catch (RetrofitError err) {
                    switch (err.getKind()) {
                        case NETWORK:
                        case HTTP:
                            EventBus.getDefault().post(new OnCreateUserFailureEvent(ErrorKind.Network));
                            break;
                        case CONVERSION:
                        case UNEXPECTED:
                            EventBus.getDefault().post(new OnCreateUserFailureEvent(ErrorKind.Unknown));
                            break;
                    }
                }
            }
        });
    }

    public class OnCreatedUserEvent {
        private final User user;

        private OnCreatedUserEvent(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public class OnCreateUserFailureEvent extends ErrorEvent {
        private OnCreateUserFailureEvent(ErrorKind kind) {
            super(kind);
        }
    }
}
