package com.example.xjapan.karaoke2.usecase.registration;

import com.example.xjapan.karaoke2.domain.event.ErrorEvent;
import com.example.xjapan.karaoke2.domain.value.ErrorKind;
import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;
import com.example.xjapan.karaoke2.domain.dao.UserDAO;
import com.example.xjapan.karaoke2.domain.entity.User;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.RetrofitError;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class RegisterMusicUseCase {

    private final UserDAO dao = UserDAO.get();
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    public void apply(final MusicTitle music) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                User user = dao.findFirst();

                if (user != null) {
                    int userId = user.accountId;

                    try {
                        AppClient.sync().createSungMusic(userId, music.getMusicId()); // no response

                        EventBus.getDefault().post(new OnCreatedSungMusicEvent());
                    } catch (RetrofitError error) {
                        EventBus.getDefault().post(new OnCreateSungMusicFailureEvent(ErrorKind.Network));
                    }
                } else {
                    EventBus.getDefault().post(new OnCreateSungMusicFailureEvent(ErrorKind.DatabaseIO));
                }
            }
        });
    }

    public class OnCreatedSungMusicEvent {

    }

    public class OnCreateSungMusicFailureEvent extends ErrorEvent {
        public OnCreateSungMusicFailureEvent(ErrorKind kind) {
            super(kind);
        }
    }
}
