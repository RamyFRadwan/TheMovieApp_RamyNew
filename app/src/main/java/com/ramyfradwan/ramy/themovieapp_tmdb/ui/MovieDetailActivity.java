package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class MovieDetailActivity extends BaseActivity<MovieDetailPresenter>
        implements MovieDetailPresenterLisener {

    private TextView rating, releaseDate, overview;
    private ImageView poster, background;
    private ConnectionStatus connectionStatus = new ConnectionStatus();
    private int id;

    private RecyclerView reviewsList, trailersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initUI();
        connectionStatus.initConnectionStatus(this);
        setupPresenter();
        if (0 != getIntent().getIntExtra(Constants.ID, 0)) {
            id = getIntent().getIntExtra(Constants.ID, 0);
            presenter.getMovieDetails(getClassName(), id);

        } else finish();

    }

    private void initUI() {
        rating = (TextView) findViewById(R.id.detail_rating);
        releaseDate = (TextView) findViewById(R.id.detail_release);
        overview = (TextView) findViewById(R.id.detail_overview);
        poster = (ImageView) findViewById(R.id.moviePoster);
        background = (ImageView) findViewById(R.id.poster);
        reviewsList = (RecyclerView) findViewById(R.id.list_item_reviews);
        trailersList = (RecyclerView) findViewById(R.id.list_item_trailers);

    }

    @Override
    protected MovieDetailPresenter setupPresenter() {
        return new MovieDetailPresenter(this, this);
    }

    @Override
    public void handleError(String errorMessage, String tag) {

    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void getMovieDetails(MovieDetailsResponse response) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) MovieDetailActivity.this;
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsingToolbarLayout);
        toolbarLayout.setTitle(response.getName());

        rating.setText(
                new StringBuilder()
                        .append(response.getVote_average())
                        .append(getString(R.string.separator))
                        .append(response.getVote_count()).toString());

        overview.setText(response.getOverview());

        releaseDate.setText(response.getRelease_date());
        Log.e("background", Constants.basePosterPath + response.getBackdropPath());

        try {

            Picasso.get()
                    .load(Constants.basePosterPath + response.getPosterPath())
                    .into(poster);
            Picasso.get()
                    .load(Constants.basePosterPath + response.getBackdropPath())
                    .into(background);
        } catch (Exception e) {
            Log.e(getResources().getString(R.string.picasso_exception), e.getMessage());
        }
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
}