package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;
import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.MoviesAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.db_provider.MovieContract;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenterListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Films. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FilmDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FilmListActivity extends BaseActivity<MoviesPresenter>
        implements
        MoviesPresenterListener,
        EndlessRecyclerViewAdapter.RequestToLoadMoreListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private String title;
    private String image_path, backdrop_path;
    private String overView;
    private String date;
    private double rating;
    private long Movie_id;

    private ImageView no_favourites_icon;

    private LinearLayoutManager mStaggeredLayoutManager;
    private boolean mTwoPane, fav;
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
        setContentView(R.layout.activity_film_list);

        if (findViewById(R.id.film_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        initUI();

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600
                && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)) {
            mStaggeredLayoutManager = new GridLayoutManager(this, 1);
        } else if (config.smallestScreenWidthDp < 600
                && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            mStaggeredLayoutManager = new GridLayoutManager(this, 3);
        } else {
            mStaggeredLayoutManager = new GridLayoutManager(this, 2);
        }

        movieRV.setLayoutManager(mStaggeredLayoutManager);

        movieRV.setHasFixedSize(true);

        connectionStatus.initConnectionStatus(this);
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
                        fav = false;
                        no_favourites_icon.setVisibility(View.GONE);
                        jellyToggleButton.setBackgroundColor(Color.TRANSPARENT);
                        //reset the counters before the call
                        pageIndex = 1;
                        pageCount = 0;

                        //Change SortType
                        sortType = Constants.GET_TOP_MOVIES;

                        /// /Make a new call
                        presenter.getTopRatedMovies(
                                getClassName()
                                , pageIndex);
                    }
                    if (state.name().equalsIgnoreCase(getString(R.string.left))) {
                        //reset the recyclerViewAdapter
                        moviesAdapter.clear();
                        fav = false;
                        no_favourites_icon.setVisibility(View.GONE);
                        jellyToggleButton.setBackgroundColor(Color.BLUE);

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
        no_favourites_icon = this.findViewById(R.id.nofavs);
        no_favourites_icon.setVisibility(View.GONE);
    }


    @Override
    protected MoviesPresenter setupPresenter() {
        return new MoviesPresenter(this);
    }

    @Override
    public void getMoviesResponse(MoviesResponse moviesResponse) {

    }

    @Override
    public void getMovies(List<Movie> movies, int totalPages) {
        this.pageCount = totalPages;
        if (pageIndex > 1) {

            moviesAdapter.appendItems(movies);
            movieRV.setAdapter(endlessRecyclerViewAdapter);

        } else {
            moviesAdapter =
                    new MoviesAdapter(this, this, movies, mTwoPane, fav);
            endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(moviesAdapter, this);
            movieRV.setAdapter(endlessRecyclerViewAdapter);

        }

    }

    @Override
    public void handleError(String errorMessage, String tag) {

    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex <= pageCount) {
            pageIndex++;
            presenter.loadMorePages(getClassName(), pageIndex, sortType);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        MenuItem action_show_fav = menu.findItem(R.id.viewFav);

        action_show_fav.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                moviesAdapter.clear();
                fav = true;
                fetchFavouritesMovies();
                jellyToggleButton.setBackgroundColor(Color.GRAY);
                return true;
            }
        });
        return true;
    }


    //Fetch Favourite Movies
    private void fetchFavouritesMovies() {
        List<Movie> favouriteMovies = new ArrayList<>();
        final Cursor c = getContentResolver()
                .query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
        if (c != null && c.moveToFirst()) {

            do {
                image_path = c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE));
                title = c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
                overView = c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
                date = c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATE));
                rating = c.getDouble(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING));
                Movie_id = c.getLong(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
                backdrop_path = c.getString(c.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP));

                Movie movie = new Movie(Movie_id, title, overView, date, image_path, backdrop_path, rating);
                favouriteMovies.add(movie);
            } while (c.moveToNext());
        }

        assert c != null;
        c.close();
        if (favouriteMovies.size() > 0) {
            no_favourites_icon.setVisibility(View.GONE);
            movieRV.setVisibility(View.VISIBLE);
            moviesAdapter =
                    new MoviesAdapter(this, this, favouriteMovies, mTwoPane, fav);
            endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(moviesAdapter, this);
            movieRV.setAdapter(endlessRecyclerViewAdapter);

        } else {
            no_favourites_icon.setVisibility(View.VISIBLE);
        }

    }
}