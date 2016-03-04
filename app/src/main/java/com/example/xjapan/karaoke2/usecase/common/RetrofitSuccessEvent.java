package com.example.xjapan.karaoke2.usecase.common;

import retrofit.client.Response;

/**
 * Created by xjapan on 16/03/04.
 */
public class RetrofitSuccessEvent<T> {
    private final T result;
    private final Response response;

    public RetrofitSuccessEvent(T result, Response response){
        this.result = result;
        this.response = response;
    }

    public T getResult(){
        return result;
    }

    public Response getResponse(){
        return response;
    }
}
