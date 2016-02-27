package com.example.xjapan.karaoke2.usecase.registration;

import com.example.xjapan.karaoke2.domain.value.ErrorKind;
import com.example.xjapan.karaoke2.domain.value.Paging;
import com.example.xjapan.karaoke2.infra.api.AppClient;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.RetrofitError;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class FetchMusicTitlesByMusicNameUseCase implements FetchMusicTitlesUseCase {

    private final ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    public void apply(final String query, final Paging paging) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<MusicTitle> musics = AppClient.sync().fetchMusicTitlesByMusicName(query, paging.getNumber(), paging.getLimit());

                    if (musics != null) {
                        EventBus.getDefault().post(new OnFetchedMusicTitlesEvent(musics));
                    } else {
                        EventBus.getDefault().post(new OnFetchMusicTitlesFailureEvent(ErrorKind.Unknown));
                    }
                } catch (RetrofitError error) {
                    EventBus.getDefault().post(new OnFetchMusicTitlesFailureEvent(ErrorKind.Network));
                }
            }
        });
    }
}
