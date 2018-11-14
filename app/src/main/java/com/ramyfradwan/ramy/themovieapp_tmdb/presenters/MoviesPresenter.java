package com.ramyfradwan.ramy.themovieapp_tmdb.presenters;

import android.support.annotation.NonNull;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BasePresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseResponseModel;
import com.ramyfradwan.ramy.themovieapp_tmdb.controllers.MoviesController;
import com.ramyfradwan.ramy.themovieapp_tmdb.controllers.MoviesControllerListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;

public class MoviesPresenter extends BasePresenter<MoviesController, MoviesPresenterListener>
        implements MoviesControllerListener {
    private MoviesPresenterListener moviesPresenterListener;
    private ConnectionStatus connectionStatus;

    public MoviesPresenter(MoviesPresenterListener moviesPresenterListener) {
        controller = new MoviesController(this);
        this.listener = moviesPresenterListener;
        connectionStatus = new ConnectionStatus();
    }

    public void getPopularMovies(@NonNull final String className, int pageIndex) {
        try {
            if (Constants.CONNECTED == connectionStatus.getStatus()) {
                controller.getPopularMovies(className, pageIndex);
                if( null != connectionStatus.tvConnectionStatus)
                    connectionStatus.tvConnectionStatus.setText(R.string.connected);
            } else if(null != connectionStatus.tvConnectionStatus)
                connectionStatus.tvConnectionStatus.setText(R.string.no_internet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTopRatedMovies(@NonNull final String className, int pageIndex) {
        try {
            if (Constants.CONNECTED == connectionStatus.getStatus()) {
                controller.getTopRatedMovies(className, pageIndex);
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
            if (response instanceof MoviesResponse)
                listener.getMovies(((MoviesResponse) response).getResults(), ((MoviesResponse) response).getTotalPages());
        }
    }

    public void loadMorePages(@NonNull final String className,
                              int pageIndex,
                              @NonNull final String sortType) {
        if (Constants.GET_POP_MOVIES.equalsIgnoreCase(sortType))
            controller.getPopularMovies(className, pageIndex);
        else if (Constants.GET_TOP_MOVIES.equalsIgnoreCase(sortType))
            controller.getTopRatedMovies(className, pageIndex);

    }
}

