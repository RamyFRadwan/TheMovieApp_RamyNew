package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoviesMainActivity extends BaseActivity<MoviesPresenter>
        implements MoviesPresenterListener, EndlessRecyclerViewAdapter.RequestToLoadMoreListener {

    private List<Movie> movies = new ArrayList<>();
    private RecyclerView movieRV;
    private JellyToggleButton jellyToggleButton;
    private String sortType = Constants.GET_POP_MOVIES;
    private int pageIndex = 1;
    private MoviesAdapter moviesAdapter;
    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
    private int pageCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_main);
        initUI();
        setupPresenter();

        //Load first page
        presenter.getPopularMovies(getClassName(), pageIndex);

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View v = super.onCreateView(name, context, attrs);

        if (null != jellyToggleButton) {
            jellyToggleButton.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
                @Override
                public void onStateChange(float process, State state, JellyToggleButton jtb) {
                    Toast.makeText(getApplicationContext(), state.name(), Toast.LENGTH_LONG).show();
                    if (state.name().equalsIgnoreCase(getString(R.string.right)))
                        sortType = Constants.GET_TOP_MOVIES;
                    if (state.name().equalsIgnoreCase(getString(R.string.left)))
                        sortType = Constants.GET_POP_MOVIES;
                }
            });

        }
        return v;
    }


    private void initUI() {
        jellyToggleButton = (JellyToggleButton) this.findViewById(R.id.moviesTypeToggle);
        movieRV = (RecyclerView) this.findViewById(R.id.moviesList);

    }

    @Override
    protected MoviesPresenter setupPresenter() {
        return new MoviesPresenter(this, this);
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
    public void getMovies(List<Movie> movies , int pageCount) {
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
        for (pageIndex = 2; pageIndex <= pageCount ; pageIndex++) {
            presenter.loadMorePages(getClassName(), pageIndex, sortType);

        }
    }
}
