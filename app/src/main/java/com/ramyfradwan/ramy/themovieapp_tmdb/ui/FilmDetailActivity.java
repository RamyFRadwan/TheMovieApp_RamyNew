package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.ReviewsAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.TrailersAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Review;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Trailer;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MovieDetailPresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MovieDetailPresenterLisener;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;

import java.util.ArrayList;
import java.util.Objects;

/**
 * An activity representing a single Film detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link FilmListActivity}.
 */
public class FilmDetailActivity extends BaseActivity<MovieDetailPresenter>
        implements MovieDetailPresenterLisener {

    private ConnectionStatus connectionStatus = new ConnectionStatus();
    private int id;
    private RecyclerView reviewsList, trailersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        connectionStatus.initConnectionStatus(this);

        setupPresenter();

        if (0 != getIntent().getIntExtra(Constants.ID, 0)) {
            id = getIntent().getIntExtra(Constants.ID, 0);
            presenter.getMovieDetails(getClassName(), id);

        } else finish();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
//        if (savedInstanceState == null) {
//            // Create the detail fragment and add it to the activity
//            // using a fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(FilmDetailFragment.ARG_ITEM_ID,
//                    getIntent().getStringExtra(FilmDetailFragment.ARG_ITEM_ID));
//            arguments.putSerializable("Movie",movie);
//            FilmDetailFragment fragment = new FilmDetailFragment();
//            fragment.setArguments(arguments);
//            getFragmentManager().beginTransaction()
//                    .add(R.id.film_detail_container, fragment)
//                    .commit();
//        }
    }

    @Override
    protected MovieDetailPresenter setupPresenter() {
        return new MovieDetailPresenter(this, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, FilmListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getMovieDetails(MovieDetailsResponse response) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) FilmDetailActivity.this;
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsingToolbarLayout);
        toolbarLayout.setTitle(response.getName());
//        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(FilmDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(FilmDetailFragment.ARG_ITEM_ID));
            arguments.putSerializable("Movie",response);
            FilmDetailFragment fragment = new FilmDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.film_detail_container, fragment)
                    .commit();
//        }
//
    }

    @Override
    public void onTrailersRetrieved(ArrayList<Trailer> trailers) {
        TrailersAdapter trailersAdapter;
        if (null != trailers) {
            trailersAdapter =
                    new TrailersAdapter(this, trailers);
            trailersList.setLayoutManager(new LinearLayoutManager(this));
            trailersList.setAdapter(trailersAdapter);
        }
    }

    @Override
    public void onReviewsRetrieved(ArrayList<Review> reviews) {
        ReviewsAdapter reviewsAdapter;
        if (null != reviews) {
            reviewsAdapter =
                    new ReviewsAdapter(this, reviews);
            reviewsList.setLayoutManager(new LinearLayoutManager(this));
            reviewsList.setAdapter(reviewsAdapter);
        }
    }

    @Override
    public void handleError(String errorMessage, String tag) {

    }

    @Override
    public void onConnectionError() {

    }
}
