package com.example.xjapan.karaoke2.model;

import java.io.Serializable;

/**
 * Created by xjapan on 16/02/24.
 */
public class MusicTitle implements Serializable {
    private int music_id;
    private String artist;
    private String title;

    public int getMusicId() {
        return music_id;
    }

    public void setMusicId(int music_id) {
        this.music_id = music_id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
