package com.example.xjapan.karaoke2.presentation.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.xjapan.karaoke2.common.ThinList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jmatsu on 2016/02/27.
 */
public abstract class ArrayRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements ThinList<T> {

    private final Context context;
    private final List<T> items;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public ArrayRecyclerAdapter(@NonNull Context context) {
        if (context.getApplicationContext() == context) {
            throw new AssertionError("Provided application context?");
        }

        this.context = context;
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    public Context getContext() {
        return context;
    }


    @Override
    public boolean add(T item) {
        return items.add(item);
    }

    @Override
    public void addAll(Collection<T> items) {
        this.items.addAll(items);
    }

    @Override
    public void addAll(int position, Collection<T> items) {
        this.items.addAll(position, items);
    }

    @Override
    public T get(int position) {
        return items.get(position);
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return items.listIterator();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    protected boolean dispatchItemClick(int position, VH holder) {
        if (onItemClickListener != null) {
            return onItemClickListener.onItemClick(position, holder);
        }

        return false;
    }

    protected boolean dispatchItemLongClick(int position, VH holder) {
        if (onItemLongClickListener != null) {
            return onItemLongClickListener.onItemLongClick(position, holder);
        }

        return false;
    }

    public interface OnItemClickListener<VH> {
        boolean onItemClick(int position, VH holder);
    }

    public interface OnItemLongClickListener<VH> {
        boolean onItemLongClick(int position, VH holder);
    }
}
