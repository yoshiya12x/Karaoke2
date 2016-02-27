package com.example.xjapan.karaoke2.presentation.activity.suggestion;

import android.support.annotation.NonNull;

import com.example.xjapan.karaoke2.R;
import com.example.xjapan.karaoke2.presentation.activity.search.SearchSangMusicActivity;

/**
 * Created by jmatsu on 2016/02/27.
 */
public enum SuggestionMenu {
    Home(android.R.id.home) {
        @Override
        public boolean doAction(@NonNull SuggestionActivity activity) {
            activity.leave();
            activity.finish();
            return true;
        }
    },
    Search(R.id.action_sang_music) {
        @Override
        public boolean doAction(@NonNull SuggestionActivity activity) {
            activity.startActivity(SearchSangMusicActivity.createIntent(activity));
            return true;
        }
    },
    Leave(R.id.action_logout) {
        @Override
        public boolean doAction(@NonNull SuggestionActivity activity) {
            activity.leave();
            return true;
        }
    },
    Reload(R.id.action_reload) {
        @Override
        public boolean doAction(@NonNull SuggestionActivity activity) {
            activity.reload();
            return true;
        }
    };

    private int id;

    SuggestionMenu(int id) {
        this.id = id;
    }

    public static SuggestionMenu fromId(int id) {
        switch (id) {
            case android.R.id.home:
                return Home;
            case R.id.action_sang_music:
                return Search;
            case R.id.action_logout:
                return Leave;
            case R.id.action_reload:
                return Reload;
            default:
                return null;
        }
    }

    public int getId() {
        return id;
    }

    public boolean doAction(@NonNull SuggestionActivity activity) {
        throw new AssertionError("doAction() must be overridden");
    }
}
