package com.example.xjapan.karaoke2.usecase.common;

/**
 * Created by xjapan on 16/03/04.
 */
public interface FailureCallback<T>{
    void onFailure(T error);
}