package com.example.xjapan.karaoke2.common;

import java.util.Collection;

/**
 * Created by jmatsu on 2016/02/27.
 */
public interface ThinList<T> extends Iterable<T> {
    boolean add(T item);
    void addAll(Collection<T> items);
    void addAll(int position, Collection<T> items);

    T get(int position);

    void clear();
}
