package com.ramyfradwan.ramy.themovieapp_tmdb.presenters;


import com.ramyfradwan.ramy.themovieapp_tmdb.base.BasePresenterListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;

import java.util.List;

public interface MoviesPresenterListener extends BasePresenterListener {
    void getMoviesResponse(MoviesResponse moviesResponse);

    void getMovies(List<Movie> movies, int totalPages);

}
