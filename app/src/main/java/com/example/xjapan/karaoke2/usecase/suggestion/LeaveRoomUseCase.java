package com.example.xjapan.karaoke2.usecase.suggestion;

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
public class LeaveRoomUseCase {
    private final UserDAO dao = UserDAO.get();
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    public void apply() {
        service.submit(new Runnable() {
            @Override
            public void run() {
                User user = dao.findFirst();

                if (user != null) {
                    int userId = user.accountId;

                    try {
                        if (AppClient.sync().leaveRoom(userId) != null) {
                            EventBus.getDefault().post(new LeavedRoomEvent());
                        } else {
                            EventBus.getDefault().post(new LeaveRoomFailureEvent(ErrorKind.Unknown));
                        }
                    } catch (RetrofitError error) {
                        EventBus.getDefault().post(new LeaveRoomFailureEvent(ErrorKind.Network));
                    }
                } else {
                    EventBus.getDefault().post(new LeaveRoomFailureEvent(ErrorKind.DatabaseIO));
                }
            }
        });
    }

    public class LeavedRoomEvent {

    }

    public class LeaveRoomFailureEvent extends ErrorEvent {

        public LeaveRoomFailureEvent(ErrorKind kind) {
            super(kind);
        }
    }
}
