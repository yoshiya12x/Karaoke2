package com.example.xjapan.karaoke2.usecase.common;

/**
 * Created by jmatsu on 2016/02/27.
 */
public interface FailureCallback<T> {
    void onFailure(T error);
}
