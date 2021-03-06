package com.ramyfradwan.ramy.themovieapp_tmdb.utils.network;


import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieReviewsResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieTrailerResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConfigApi {
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<MoviesResponse> getMostPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);


    @GET("movie/{id}/reviews")
    Call<MovieReviewsResponse> getMovieReview(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<MovieTrailerResponse> getMovieTrailer(@Path("id") int movieId, @Query("api_key") String apiKey);

//        @GET(" http://image.tmdb.org/t/p/w185//")
}
