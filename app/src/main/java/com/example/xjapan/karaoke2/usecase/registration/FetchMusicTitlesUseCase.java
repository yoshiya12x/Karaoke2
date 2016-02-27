package com.example.xjapan.karaoke2.usecase.registration;

import com.example.xjapan.karaoke2.domain.event.ErrorEvent;
import com.example.xjapan.karaoke2.domain.value.ErrorKind;
import com.example.xjapan.karaoke2.domain.value.Paging;
import com.example.xjapan.karaoke2.infra.api.entity.MusicRecommend;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jmatsu on 2016/02/27.
 */
public interface FetchMusicTitlesUseCase {
    void apply(String query, Paging paging);

    class OnFetchedMusicTitlesEvent {
        private final Collection<MusicTitle> musics;

        protected OnFetchedMusicTitlesEvent(List<MusicTitle> musics) {
            this.musics = Collections.unmodifiableCollection(musics);
        }

        public Collection<MusicTitle> getMusics() {
            return musics;
        }
    }

    class OnFetchMusicTitlesFailureEvent extends ErrorEvent {

        public OnFetchMusicTitlesFailureEvent(ErrorKind kind) {
            super(kind);
        }
    }
}
