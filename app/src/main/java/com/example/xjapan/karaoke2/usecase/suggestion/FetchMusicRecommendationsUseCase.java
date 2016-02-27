package com.example.xjapan.karaoke2.usecase.suggestion;

import com.example.xjapan.karaoke2.domain.dao.UserDAO;
import com.example.xjapan.karaoke2.domain.entity.User;
import com.example.xjapan.karaoke2.domain.event.ErrorEvent;
import com.example.xjapan.karaoke2.domain.value.ErrorKind;
import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.infra.api.entity.MusicRecommend;

import org.greenrobot.eventbus.EventBus;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.RetrofitError;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class FetchMusicRecommendationsUseCase {

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
                        List<MusicRecommend> recommendations = AppClient.sync().fetchMusicRecommendations(userId);

                        if (recommendations != null) {
                            EventBus.getDefault().post(new OnFetchedMusicRecommendationsEvent(recommendations));
                        } else {
                            EventBus.getDefault().post(new OnFetchMusicRecommendationsFailureEvent(ErrorKind.Unknown));
                        }
                    } catch (RetrofitError error) {
                        EventBus.getDefault().post(new OnFetchMusicRecommendationsFailureEvent(ErrorKind.Network));
                    }
                } else {
                    EventBus.getDefault().post(new OnFetchMusicRecommendationsFailureEvent(ErrorKind.DatabaseIO));
                }
            }
        });
    }

    public class OnFetchedMusicRecommendationsEvent {
        private final Collection<MusicRecommend> recommendations;

        private OnFetchedMusicRecommendationsEvent(List<MusicRecommend> recommendations) {
            this.recommendations = Collections.unmodifiableCollection(recommendations);
        }

        public Collection<MusicRecommend> getRecommendations() {
            return recommendations;
        }
    }

    public class OnFetchMusicRecommendationsFailureEvent extends ErrorEvent {

        public OnFetchMusicRecommendationsFailureEvent(ErrorKind kind) {
            super(kind);
        }
    }
}
