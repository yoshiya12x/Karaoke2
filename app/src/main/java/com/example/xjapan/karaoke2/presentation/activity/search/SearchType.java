package com.example.xjapan.karaoke2.presentation.activity.search;

import com.example.xjapan.karaoke2.R;

/**
 * Created by jmatsu on 2016/02/27.
 */
public enum SearchType {
    MUSIC(R.string.title__register__format__music) {
        @Override
        public TriggerEvent getEvent(String query) {
            return new MusicTriggerEvent(query);
        }
    },
    ARTIST(R.string.title__register__format__artist) {
        @Override
        public TriggerEvent getEvent(String query) {
            return new ArtistTriggerEvent(query);
        }
    };

    private int id;

    SearchType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public TriggerEvent getEvent(String query) {
        throw new AssertionError("Forget to implement this method");
    }

    private interface TriggerEvent { String getQuery(); }

    public static class MusicTriggerEvent implements TriggerEvent {
        private final String query;

        private MusicTriggerEvent(String query) {
            this.query = query;
        }

        @Override
        public String getQuery() {
            return query;
        }
    }

    public static class ArtistTriggerEvent implements TriggerEvent {
        private final String query;

        private ArtistTriggerEvent(String query) {
            this.query = query;
        }

        @Override
        public String getQuery() {
            return query;
        }
    }
}
