package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MovieDetailPresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MovieDetailPresenterLisener;

public class MovieDetailActivity extends BaseActivity<MovieDetailPresenter>
        implements MovieDetailPresenterLisener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }

    @Override
    protected MovieDetailPresenter setupPresenter() {
        return null;
    }

    @Override
    public void handleError(String errorMessage, String tag) {

    }

    @Override
    public void onConnectionError() {

    }
}
