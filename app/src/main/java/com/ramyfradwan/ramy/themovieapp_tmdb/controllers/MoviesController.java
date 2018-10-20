package com.ramyfradwan.ramy.themovieapp_tmdb.controllers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseCoreController;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ApiClient;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConfigApi;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesController extends BaseCoreController {
    private MoviesControllerListener moviesControllerListener;

    public MoviesController(MoviesControllerListener moviesControllerListener) {
        super(moviesControllerListener);
        this.moviesControllerListener = moviesControllerListener;
        this.listener = (MoviesControllerListener) moviesControllerListener;
    }

    public void getPopularMovies(@NonNull final String className, int pageIndex) {

        ConfigApi apiService =
                ApiClient.getClient().create(ConfigApi.class);

        Call<MoviesResponse> call = apiService.getMostPopularMovies(Constants.API_KEY, pageIndex);
        Log.e("Request Movie", Objects.requireNonNull(call.request()).toString());

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (null != response.body())
                    Log.e("movies count  :  ", response.body().getTotalPages() + "");

                moviesControllerListener.onFinishController(response.body(), Constants.GET_POP_MOVIES);
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(className, t.toString());
            }
        });
    }

    public void getTopRatedMovies(@NonNull final String className, int pageIndex) {

        ConfigApi apiService =
                ApiClient.getClient().create(ConfigApi.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(Constants.API_KEY, pageIndex);
        Log.e("Request Movie", call.toString());
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                moviesControllerListener.onFinishController(response.body(), Constants.GET_TOP_MOVIES);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(className, t.toString());
            }
        });
    }
}
