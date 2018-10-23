package com.ramyfradwan.ramy.themovieapp_tmdb.presenters;

import android.support.annotation.NonNull;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BasePresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseResponseModel;
import com.ramyfradwan.ramy.themovieapp_tmdb.controllers.MovieDetailController;
import com.ramyfradwan.ramy.themovieapp_tmdb.controllers.MovieDetailControllerListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Review;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Trailer;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;

import java.util.ArrayList;

public class MovieDetailPresenter extends BasePresenter<MovieDetailController, MovieDetailPresenterLisener>
        implements MovieDetailControllerListener {
    private ConnectionStatus connectionStatus;

    public MovieDetailPresenter( MovieDetailPresenterLisener movieDetailPresenterLisener) {
        controller = new MovieDetailController(this);
        connectionStatus = new ConnectionStatus();
        this.listener = movieDetailPresenterLisener;
    }


    public void getMovieDetails(@NonNull final String className,
                                int movieId) {
        try {
            if (Constants.CONNECTED == connectionStatus.getStatus()) {
                controller.getMovieDetails(className, movieId);
                controller.getMovieReviews(className, movieId);
                controller.getMovieTrailers(className, movieId);
                connectionStatus.tvConnectionStatus.setText(R.string.connected);
            } else
                connectionStatus.tvConnectionStatus.setText(R.string.no_internet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinishController(BaseResponseModel response, String tag) {
        super.onFinishController(response, tag);
        if (null != response) {
            if (response instanceof MovieDetailsResponse) {
                listener.getMovieDetails((MovieDetailsResponse) response);
            }
        }
    }

    @Override
    public void onTrailersRetrieved(ArrayList<Trailer> trailers) {
        listener.onTrailersRetrieved(trailers);
    }

    @Override
    public void onReviewsRetrieved(ArrayList<Review> reviews) {
        listener.onReviewsRetrieved(reviews);
    }
}