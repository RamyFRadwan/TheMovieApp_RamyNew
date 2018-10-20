package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;
import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.MoviesAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenterListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

import java.util.List;

public class MoviesMainActivity extends BaseActivity<MoviesPresenter>
        implements MoviesPresenterListener, EndlessRecyclerViewAdapter.RequestToLoadMoreListener {

    private RecyclerView movieRV;
    private JellyToggleButton jellyToggleButton;
    private String sortType = Constants.GET_POP_MOVIES;
    private int pageIndex = 1;
    private MoviesAdapter moviesAdapter;
    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
    private int pageCount = 0;
    private ConnectionStatus connectionStatus = new ConnectionStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_main);
        initUI();
        connectionStatus.initConnectionStatus(this);
        setupPresenter();
        moviesAdapter =
                new MoviesAdapter(this);

        //Load first page
        presenter.getPopularMovies(getClassName(), pageIndex);

        if (null != jellyToggleButton) {
            jellyToggleButton.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
                @Override
                public void onStateChange(float process, State state, JellyToggleButton jtb) {
                    if (state.name().equalsIgnoreCase(getString(R.string.right))) {
                        //reset the recyclerViewAdapter
                        moviesAdapter.clear();

                        //reset the counters before the call
                        pageIndex = 1;
                        pageCount = 0;

                        //Change SortType
                        sortType = Constants.GET_TOP_MOVIES;

                        /// /Make a new call
                        presenter.getTopRatedMovies(getClassName(), pageIndex);
                    }
                    if (state.name().equalsIgnoreCase(getString(R.string.left))) {
                        //reset the recyclerViewAdapter
                        moviesAdapter.clear();

                        //reset the counters before the call
                        pageCount = 0;
                        pageIndex = 1;

                        //Change SortType
                        sortType = Constants.GET_POP_MOVIES;
                        /// /Make a new call
                        presenter.getPopularMovies(getClassName(), pageIndex);
                    }
                }
            });

        }
    }


    private void initUI() {
        jellyToggleButton = this.findViewById(R.id.moviesTypeToggle);
        movieRV = this.findViewById(R.id.moviesList);

    }

    @Override
    protected MoviesPresenter setupPresenter() {
        return new MoviesPresenter(this);
    }

    @Override
    public void handleError(String errorMessage, String tag) {

    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void getMoviesResponse(MoviesResponse moviesResponse) {

    }

    @Override
    public void getMovies(List<Movie> movies, int pageCount) {
        this.pageCount = pageCount;
        if (pageIndex > 1) {

            moviesAdapter.appendItems(movies);
            movieRV.setAdapter(endlessRecyclerViewAdapter);

        } else {
            moviesAdapter =
                    new MoviesAdapter(this, movies);
            endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(moviesAdapter, this);
            movieRV.setLayoutManager(new GridLayoutManager(this, 2));
            movieRV.setAdapter(endlessRecyclerViewAdapter);

        }

    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex <= pageCount) {
            pageIndex++;
            presenter.loadMorePages(getClassName(), pageIndex, sortType);

        }
    }
}

