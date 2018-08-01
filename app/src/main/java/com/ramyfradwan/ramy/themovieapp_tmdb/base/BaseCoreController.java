package com.ramyfradwan.ramy.themovieapp_tmdb.base;

import android.support.annotation.Nullable;

public abstract class BaseCoreController<T> {

    @Nullable
    protected T listener;
    private String userName;

    public BaseCoreController(T listener) {
        this.listener = listener;
    }



    public void destroy() {
        listener = null;
    }
}
