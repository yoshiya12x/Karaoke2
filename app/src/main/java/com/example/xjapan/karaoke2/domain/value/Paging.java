package com.example.xjapan.karaoke2.domain.value;

import java.io.Serializable;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class Paging implements Serializable {
    private int number;
    private int limit;

    public Paging(int limit) {
        this(0, limit);
    }

    public Paging(int number, int limit) {
        this.number = number;
        this.limit = limit;
    }

    public int getNumber() {
        return number;
    }

    public int getLimit() {
        return limit;
    }

    public Paging next() {
        return new Paging(number + 1, limit);
    }

    public Paging prev() {
        return new Paging(number - 1, limit);
    }

    public boolean hasNext() {
        return false; // FIXME
    }

    public boolean hasPrev() {
        return number > 0;
    }
}
