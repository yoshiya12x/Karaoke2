package com.example.xjapan.karaoke2.usecase.initialization;

import android.support.annotation.NonNull;

import com.example.xjapan.karaoke2.domain.dao.UserDAO;
import com.example.xjapan.karaoke2.domain.entity.User;
import com.example.xjapan.karaoke2.domain.event.ErrorEvent;
import com.example.xjapan.karaoke2.domain.value.ErrorKind;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class CheckRegisteredUserUseCase {
    private final UserDAO dao = UserDAO.get();

    public void apply() {
        User user = dao.findFirst();

        if (user != null) {
            EventBus.getDefault().post(new OnExistEvent(user));
        } else {
            EventBus.getDefault().post(new OnNotExistEvent(ErrorKind.Unknown));
        }
    }

    public class OnExistEvent {
        private final User user;

        private OnExistEvent(@NonNull User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public class OnNotExistEvent extends ErrorEvent {
        private OnNotExistEvent(ErrorKind kind) {
            super(kind);
        }
    }
}
