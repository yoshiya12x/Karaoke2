package com.example.xjapan.karaoke2.error;

/**
 * Created by xjapan on 16/03/05.
 */
public class ErrorEvent {
    private final ErrorKind kind;

    public ErrorEvent(ErrorKind kind){
        this.kind = kind;
    }

    public ErrorKind getKind(){
        return kind;
    }
}
