package com.ramyfradwan.ramy.themovieapp_tmdb.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ramyfradwan.ramy.themovieapp_tmdb.base.BasePresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseResponseModel;
import com.ramyfradwan.ramy.themovieapp_tmdb.controllers.MoviesController;
import com.ramyfradwan.ramy.themovieapp_tmdb.controllers.MoviesControllerListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;

import java.util.List;

public class MoviesPresenter extends BasePresenter<MoviesController, MoviesPresenterListener> implements MoviesControllerListener {
    private MoviesPresenterListener moviesPresenterListener;
    private Context context;

    public MoviesPresenter(Context context, MoviesPresenterListener moviesPresenterListener) {
        this.context = context;
        controller = new MoviesController(this, context);
        this.listener = moviesPresenterListener;
    }

    public void getPopularMovies(@NonNull final String className, int pageIndex) {
        controller.getPopularMovies(className, pageIndex);
    }

    public void getTopRatedMovies(@NonNull final String className, int pageIndex) {
        controller.getTopRatedMovies(className, pageIndex);
    }

    @Override
    public void onFinishController(BaseResponseModel response, String tag) {
        super.onFinishController(response, tag);
        if (null != response){
            if (response instanceof MoviesResponse)
                listener.getMovies(((MoviesResponse) response).getResults(),((MoviesResponse) response).getTotalPages());
        }
    }

    public void loadMorePages(@NonNull final  String className,
                                     int pageIndex,
                              @NonNull final String sortType){
        if (Constants.GET_POP_MOVIES.equalsIgnoreCase(sortType))
            controller.getPopularMovies(className,pageIndex);
        else if (Constants.GET_TOP_MOVIES.equalsIgnoreCase(sortType))
            controller.getTopRatedMovies(className,pageIndex);

    }
}

