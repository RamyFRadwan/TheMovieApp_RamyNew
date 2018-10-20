package com.ramyfradwan.ramy.themovieapp_tmdb.controllers;

import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseCoreControllerListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Review;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Trailer;

import java.util.ArrayList;

public interface MovieDetailControllerListener extends BaseCoreControllerListener {
    void onTrailersRetrieved(ArrayList<Trailer> trailers);

    void onReviewsRetrieved(ArrayList<Review> reviews);
}