package com.example.xjapan.karaoke2.usecase.main;

import com.example.xjapan.karaoke2.domain.dao.UserDAO;
import com.example.xjapan.karaoke2.domain.entity.User;
import com.example.xjapan.karaoke2.domain.event.ErrorEvent;
import com.example.xjapan.karaoke2.domain.value.ErrorKind;
import com.example.xjapan.karaoke2.infra.api.AppClient;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.RetrofitError;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class EnterRoomUseCase {
    private final UserDAO dao = UserDAO.get();
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    public void apply(final String name) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                User user = dao.findFirst();

                if (user != null) {
                    int userId = user.accountId;

                    try {
                        if (AppClient.sync().enterRoom(userId, name) != null) {
                            EventBus.getDefault().post(new OnEnteredRoomEvent(name));
                        } else {
                            EventBus.getDefault().post(new OnEnterRoomFailureEvent(ErrorKind.Unknown));
                        }
                    } catch (RetrofitError error) {
                        EventBus.getDefault().post(new OnEnterRoomFailureEvent(ErrorKind.Missing));
                    }
                } else {
                    EventBus.getDefault().post(new OnEnterRoomFailureEvent(ErrorKind.DatabaseIO));
                }
            }
        });
    }

    public class OnEnteredRoomEvent {
        private final String name;

        private OnEnteredRoomEvent(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public class OnEnterRoomFailureEvent extends ErrorEvent {
        public OnEnterRoomFailureEvent(ErrorKind kind) {
            super(kind);
        }
    }
}
