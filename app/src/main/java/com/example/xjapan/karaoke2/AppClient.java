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

        @GET("/search_music_title_ranged")
        void getSearchMusicTitleByMusicName(
                @Query("music_name") String music_name,
                @Query("page_length") int page_length,
                @Query("page_number") int page_number,
                Callback<List<MusicTitle>> callback
        );

        @GET("/search_music_artist_ranged")
        void getSearchMusicTitleByArtistName(
                @Query("artist_name") String artist_name,
                @Query("page_length") int page_length,
                @Query("page_number") int page_number,
                Callback<List<MusicTitle>> callback
        );

        @GET("/search_music_title_ranged_fast")
        void getSearchMusicTitleFastByMusicName(
                @Query("music_name") String music_name,
                @Query("page_length") int page_length,
                @Query("page_number") int page_number,
                Callback<List<MusicTitle>> callback
        );

        @GET("/search_music_artist_ranged_fast")
        void getSearchMusicTitleFastByArtistName(
                @Query("artist_name") String artist_name,
                @Query("page_length") int page_length,
                @Query("page_number") int page_number,
                Callback<List<MusicTitle>> callback
        );

        @GET("/account")
        void getUserInfo(
                @Query("name") String name,
                Callback<UserInfo> callback);

        @GET("/sung_music")
        void register_sung_music(
                @Query("account_id") int account_id,
                @Query("music_id") int music_id,
                Callback<UserInfo> callback);

        @GET("/room/in")
        void roomIn(
                @Query("account_id") int account_id,
                @Query("room_name") String room_name,
                Callback<UserInfo> callback);

        @GET("/room/out")
        void roomOut(
                @Query("account_id") int account_id,
                Callback<UserInfo> callback);

        @GET("/create_room")
        void createRoom(
                @Query("room_name") String room_name,
                @Query("user_id") int user_id,
                Callback<UserInfo> callback);
    }
}
