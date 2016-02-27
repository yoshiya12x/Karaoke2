package com.example.xjapan.karaoke2.infra.api;

import com.example.xjapan.karaoke2.BuildConfig;
import com.example.xjapan.karaoke2.infra.api.entity.MusicRecommend;
import com.example.xjapan.karaoke2.infra.api.entity.MusicTitle;
import com.example.xjapan.karaoke2.domain.entity.User;
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

    private final static AppClient sInstance = new AppClient();
    private final AppService service;
    private final SyncService syncService;

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
                .setEndpoint(BuildConfig.API_END_POINT)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("=NETWORK="))
                .build()
                .create(AppService.class);

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(BuildConfig.API_END_POINT)
                .setConverter(new GsonConverter(gson));

        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new AndroidLog("= Sync Service ="));
        } else {
            builder.setLogLevel(RestAdapter.LogLevel.NONE);
        }

        syncService = builder.build().create(SyncService.class);
    }

    public static SyncService sync() { return sInstance.syncService; }

    public static AppService getService() {
        return sInstance.service;
    }

    public interface SyncService {
        @GET("/recommend")
        List<MusicRecommend> fetchMusicRecommendations(
                @Query("id") int id
        );

        @GET("/search_music_title_ranged")
        List<MusicTitle> fetchMusicTitlesByMusicName(
                @Query("music_name") String musicName,
                @Query("page_number") int number,
                @Query("page_length") int limit
        );

        @GET("/search_music_artist_ranged")
        List<MusicTitle> fetchMusicTitlesByArtistName(
                @Query("artist_name") String artistName,
                @Query("page_number") int number,
                @Query("page_length") int limit
        );

        @GET("/account")
        User createUser(
                @Query("name") String name
        );

        @GET("/sung_music")
        User createSungMusic(
                @Query("account_id") int accountId,
                @Query("music_id") int musicId
        );

        @GET("/room/in")
        User enterRoom(
                @Query("account_id") int accountId,
                @Query("room_name") String roomName
        );

        @GET("/room/out")
        User leaveRoom(
                @Query("account_id") int accountId
        );

        @GET("/create_room")
        User createRoom(
                @Query("user_id") int userId,
                @Query("room_name") String roomName
        );
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
                Callback<User> callback);

        @GET("/sung_music")
        void register_sung_music(
                @Query("account_id") int account_id,
                @Query("music_id") int music_id,
                Callback<User> callback);

        @GET("/room/in")
        void roomIn(
                @Query("account_id") int account_id,
                @Query("room_name") String room_name,
                Callback<User> callback);

        @GET("/room/out")
        void roomOut(
                @Query("account_id") int account_id,
                Callback<User> callback);

        @GET("/create_room")
        void createRoom(
                @Query("room_name") String room_name,
                @Query("user_id") int user_id,
                Callback<User> callback);
    }
}
