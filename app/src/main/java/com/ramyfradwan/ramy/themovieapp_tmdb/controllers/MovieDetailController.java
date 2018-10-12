package com.ramyfradwan.ramy.themovieapp_tmdb.controllers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseCoreController;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ApiClient;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConfigApi;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailController extends BaseCoreController<MovieDetailControllerListener> {
    private MovieDetailControllerListener movieDetailControllerListener;

    public MovieDetailController(MovieDetailControllerListener listener) {
        super(listener);
        this.movieDetailControllerListener = listener;
        this.listener = listener;
    }

    public void getMovieDetails(@NonNull final String className,
                                int movieId) {

        ConfigApi apiService =
                ApiClient.getClient().create(ConfigApi.class);

        Call<MovieDetailsResponse> call = apiService.getMovieDetails(movieId, Constants.API_KEY);
        Log.e("Request Movie", Objects.requireNonNull(call.request()).toString());

        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailsResponse>call, @NonNull Response<MovieDetailsResponse> response) {
                if (null != response.body())
                    Log.e("movies count  :  ", response.body().getName() + "");

                movieDetailControllerListener.onFinishController(response.body(), Constants.GET_MOVIE_DETAIL);
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailsResponse>call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(className, t.toString());
            }
        });
    }

}
