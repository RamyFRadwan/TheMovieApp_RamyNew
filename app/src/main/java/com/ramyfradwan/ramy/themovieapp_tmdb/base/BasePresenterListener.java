package com.ramyfradwan.ramy.themovieapp_tmdb.base;

public interface BasePresenterListener {
    void handleError(String errorMessage, String tag);

    void onConnectionError();

}
