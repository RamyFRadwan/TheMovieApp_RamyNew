package com.ramyfradwan.ramy.themovieapp_tmdb.presenters;

import com.ramyfradwan.ramy.themovieapp_tmdb.base.BasePresenterListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;

public interface MovieDetailPresenterLisener extends BasePresenterListener {
    void getMovieDetails(MovieDetailsResponse response);
}
