package com.example.xjapan.karaoke2;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by xjapan on 16/02/24.
 */
public class AppClient {

    private String END_POINT = "http://musicrecommender.herokuapp.com";
    private static AppClient sInstance = new AppClient();
    private AppService service;

    private AppClient() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
//                request.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                //request.addHeader("Authorization", "Bearer RKydQocXJc5JtE8NXTxn18kNNOWfG_AHhwR92jpXv4g");
            }
        };

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        service = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("=NETWORK="))
                .build()
                .create(AppService.class);
    }

    public static AppService getService() {
        return sInstance.service;
    }

    public interface AppService {
        @GET("/recommend")
        void getMusicRecommend(
                @Query("id") int id,
                Callback<List<MusicRecommend>> callback
        );

        @GET("")
        void getTest(
                @Query("test") int test,
                Callback<List<MusicRecommend>> callback);
    }
}
