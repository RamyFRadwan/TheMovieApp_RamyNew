package com.ramyfradwan.ramy.themovieapp_tmdb.presenters;

import com.ramyfradwan.ramy.themovieapp_tmdb.base.BasePresenterListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Review;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Trailer;

import java.util.List;

public interface MovieDetailPresenterLisener extends BasePresenterListener {
    void getMovieDetails(MovieDetailsResponse response);

    void onTrailersRetrieved(List<Trailer> trailers);

    void onReviewsRetrieved(List<Review> reviews);

}