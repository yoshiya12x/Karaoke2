package com.example.xjapan.karaoke2.usecase.search;

import com.example.xjapan.karaoke2.model.MusicTitle;
import com.example.xjapan.karaoke2.usecase.common.FailureCallback;
import com.example.xjapan.karaoke2.usecase.common.RetrofitSuccessEvent;
import com.example.xjapan.karaoke2.usecase.common.SuccessCallback;
import com.example.xjapan.karaoke2.util.AppClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by xjapan on 16/03/04.
 */
public class SearchMusicUseCase {

    private final String postName;

    public SearchMusicUseCase(String postName) {
        this.postName = postName;
    }

    public void applySearchMusicTitleByArtistName(final SuccessCallback<RetrofitSuccessEvent<List<MusicTitle>>> successCallback, final FailureCallback<RetrofitError> failureCallback) {
        assert successCallback != null;
        assert failureCallback != null;

        AppClient.getService().getSearchMusicTitleByArtistName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitles, Response response) {
                successCallback.onSuccess(new RetrofitSuccessEvent<>(musicTitles, response));
            }

            @Override
            public void failure(RetrofitError error) {
                failureCallback.onFailure(error);
            }
        });
    }

    public void applySearchMusicTitleByMusicName(final SuccessCallback<RetrofitSuccessEvent<List<MusicTitle>>> successCallback, final FailureCallback<RetrofitError> failureCallback){
        assert successCallback != null;
        assert failureCallback != null;

        AppClient.getService().getSearchMusicTitleByMusicName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitles, Response response) {
                successCallback.onSuccess(new RetrofitSuccessEvent<>(musicTitles, response));
            }

            @Override
            public void failure(RetrofitError error) {
                failureCallback.onFailure(error);
            }
        });
    }

    public void applySearchMusicTitleFasByArtistName(final SuccessCallback<RetrofitSuccessEvent<List<MusicTitle>>> successCallback, final FailureCallback<RetrofitError> failureCallback){
        assert successCallback != null;
        assert failureCallback != null;

        AppClient.getService().getSearchMusicTitleFastByArtistName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitles, Response response) {
                successCallback.onSuccess(new RetrofitSuccessEvent<>(musicTitles, response));
            }

            @Override
            public void failure(RetrofitError error) {
                failureCallback.onFailure(error);
            }
        });
    }

    public void applySearchMusicTitleFasByMusicName(final SuccessCallback<RetrofitSuccessEvent<List<MusicTitle>>> successCallback, final FailureCallback<RetrofitError> failureCallback){
        assert successCallback != null;
        assert failureCallback != null;

        AppClient.getService().getSearchMusicTitleFastByMusicName(postName, 30, 0, new Callback<List<MusicTitle>>() {
            @Override
            public void success(List<MusicTitle> musicTitles, Response response) {
                successCallback.onSuccess(new RetrofitSuccessEvent<>(musicTitles, response));
            }

            @Override
            public void failure(RetrofitError error) {
                failureCallback.onFailure(error);
            }
        });
    }
}
