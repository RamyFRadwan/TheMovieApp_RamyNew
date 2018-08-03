package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MovieDetailPresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MovieDetailPresenterLisener;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MovieDetailActivity extends BaseActivity<MovieDetailPresenter>
        implements MovieDetailPresenterLisener {

    private TextView rating, releaseDate, overview;
    private ImageView poster, background;
    private ConnectionStatus connectionStatus = new ConnectionStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initUI();
        connectionStatus.initConnectionStatus(this);
        setupPresenter();
        int id;
        if (0 != getIntent().getIntExtra(Constants.ID,0)){
            id = getIntent().getIntExtra(Constants.ID,0);
            presenter.getMovieDetails(getClassName(),id);
        }else finish();

    }

    private void initUI() {
        rating = (TextView) findViewById(R.id.detail_rating);
        releaseDate = (TextView) findViewById(R.id.detail_release);
        overview = (TextView) findViewById(R.id.detail_overview);
        poster = (ImageView) findViewById(R.id.moviePoster);
        background = (ImageView) findViewById(R.id.poster);
    }

    @Override
    protected MovieDetailPresenter setupPresenter() {
        return new MovieDetailPresenter(this,this);
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
        }catch (Exception e){
            Log.e(getResources().getString(R.string.picasso_exception),e.getMessage());
        }
    }
}
