package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.os.Bundle;
import android.view.View;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenterListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

import java.util.List;

public class MoviesMainActivity extends BaseActivity<MoviesPresenter>
        implements
        MoviesPresenterListener,
        EndlessRecyclerViewAdapter.RequestToLoadMoreListener,
        MoviesMainFragment.Callback{

    private static final String TAG_FRAGMENT = "MOVIE_FRAGMENT";

    private ConnectionStatus connectionStatus = new ConnectionStatus();
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_main);
        setupPresenter();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container, new MovieDetailFragment(), TAG_FRAGMENT)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }


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

    }

    @Override
    public void onLoadMoreRequested() {
    }

    @Override
    public void onItemSelected(Movie movie, View view) {

    }
}

