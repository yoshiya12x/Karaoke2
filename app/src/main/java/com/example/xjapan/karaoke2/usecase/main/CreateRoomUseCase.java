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
public class CreateRoomUseCase {

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
                        AppClient.sync().createRoom(userId, name); // no response

                        EventBus.getDefault().post(new OnCreatedRoomEvent(name));
                    } catch (RetrofitError error) {
                        EventBus.getDefault().post(new OnCreateRoomFailureEvent(ErrorKind.Network));
                    }
                } else {
                    EventBus.getDefault().post(new OnCreateRoomFailureEvent(ErrorKind.DatabaseIO));
                }
            }
        });
    }

    public class OnCreatedRoomEvent {
        private final String name;

        private OnCreatedRoomEvent(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public class OnCreateRoomFailureEvent extends ErrorEvent {
        public OnCreateRoomFailureEvent(ErrorKind kind) {
            super(kind);
        }
    }
}
