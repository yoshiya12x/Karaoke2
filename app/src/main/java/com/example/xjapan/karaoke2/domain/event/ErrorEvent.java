package com.example.xjapan.karaoke2.domain.event;

import com.example.xjapan.karaoke2.domain.value.ErrorKind;

/**
 * Created by jmatsu on 2016/02/27.
 */
public abstract class ErrorEvent {
    private final ErrorKind kind;

    public ErrorEvent(ErrorKind kind) {
        this.kind = kind;
    }

    public ErrorKind getKind() {
        return kind;
    }
}
