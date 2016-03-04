package com.example.xjapan.karaoke2.usecase.recommend;

import com.example.xjapan.karaoke2.model.MusicRecommend;
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
public class RecommendMusicUseCase {

    public RecommendMusicUseCase() {
    }

    public void apply(int accountId, final SuccessCallback<RetrofitSuccessEvent<List<MusicRecommend>>> successCallback, final FailureCallback<RetrofitError> failureCallback) {
        assert successCallback != null;
        assert failureCallback != null;

        AppClient.getService().getMusicRecommend(accountId, new Callback<List<MusicRecommend>>() {
            @Override
            public void success(List<MusicRecommend> musicRecommends, Response response) {
                successCallback.onSuccess(new RetrofitSuccessEvent<>(musicRecommends, response));
            }

            @Override
            public void failure(RetrofitError error) {
                failureCallback.onFailure(error);
            }
        });
    }
}
