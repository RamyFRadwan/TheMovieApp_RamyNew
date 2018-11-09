package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.ReviewsAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.TrailersAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.db_provider.DbHelper;
import com.ramyfradwan.ramy.themovieapp_tmdb.db_provider.MovieContract;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Review;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Trailer;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MovieDetailPresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MovieDetailPresenterLisener;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A fragment representing a single Film detail screen.
 * This fragment is either contained in a {@link FilmListActivity}
 * in two-pane mode (on tablets) or a {@link FilmDetailActivity}
 * on handsets.
 */
public class FilmDetailFragment extends android.app.Fragment
        implements MovieDetailPresenterLisener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final String LOG_TAG = FilmDetailFragment.class.getSimpleName();
    private boolean hasArguments;
    private MovieDetailsResponse movie;
    private boolean isFavoured;
    private MenuItem menuItem;
    private boolean trailerFound;
    public static String Poster;
    private boolean mTwoPane;
    private SharedPreferences sh;
    private String im_path;
    private String pd_path;
    private boolean fav;
    private TextView rating;
    private TextView releaseDate;
    private TextView overview;
    private ImageView poster, background;
    private RecyclerView reviewsList, trailersList;
    private View view;
    private MovieDetailPresenter presenter;
    private String date, title, overView;
    private double rating_vote;
    private Movie film;
    private MovieDetailsResponse mFilm;

    /**
     * The dummy content this fragment is presenting.
     */

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FilmDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

//            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.film_detail, container, false);
        this.view = view;
        initUI(view);
        presenter = new MovieDetailPresenter(this);
        sh = view.getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        final DbHelper db = new DbHelper(getActivity());
        Bundle arguments = getArguments();

        int id;
        if (arguments != null) {
            hasArguments = true;
            Bundle bundle = arguments.getBundle(Constants.movie);
            isFavoured = bundle.getBoolean(Constants.Fav);
            fav = bundle.getBoolean(Constants.Fav);
            id = bundle.getInt(Constants.ID);
            film = (Movie) bundle.getSerializable(Constants.FILM);
            if (id != 0 && !fav) {
                presenter.getMovieDetails(FilmDetailFragment.class.getSimpleName(), id);
            }

            if (fav && null != film) {
                mFilm =
                        new MovieDetailsResponse(film.getId(), film.getTitle(), film.getOverview(), film.getReleaseDate(), film.getPosterPath(), film.getBackdropPath(), film.getVoteAverage());
                setMovieData(mFilm);
            }
            if (arguments.getBoolean("twoPane")) {
                mTwoPane = true;
            } else {
                if (!fav) {

                    Picasso
                            .get()
                            .load(Constants.basePosterPath + movie.getBackdropPath())
                            .into(background);
                    Picasso.get()
                            .load(Constants.basePosterPath + movie.getPosterPath())
                            .into(poster);

                } else if (fav) {
                    Picasso
                            .get()
                            .load(new File(mFilm.getBackdropPath()))
                            .into(background);
                    Picasso
                            .get()
                            .load(new File(mFilm.getPosterPath()))
                            .into(poster);
                }
            }

            assert movie != null;

            final ToggleButton add_bookmark = (ToggleButton) view.findViewById(R.id.favouriteButton);
            add_bookmark.setChecked(isFavoured);
            add_bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                    //When Movie is added to favourites
                    if (isChecked) {
                        //Saving images Locally
                        saveImages();

                        // Adding Movie Values to DB
                        ContentValues values = new ContentValues();
                        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
                        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getName());
                        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movie.getBackdropPath());
                        values.put(MovieContract.MovieEntry.COLUMN_RATING, movie.getVote_average());
                        values.put(MovieContract.MovieEntry.COLUMN_DATE, movie.getRelease_date());
                        values.put(MovieContract.MovieEntry.COLUMN_IMAGE, movie.getPosterPath());

                        getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);

                        isFavoured = true;
                        add_bookmark.setText(R.string.on);

                    }
                    //When Movie is removed
                    else {
                        String[] args = {mFilm.getName()};
                            int row = getActivity().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, "title=?", args);
                            if (row > 0) {
                                Toast.makeText(getActivity(), "Movie delete Success", Toast.LENGTH_SHORT).show();
                                toggleButton.setVisibility(View.INVISIBLE);
                                getActivity().finish();
                            }
                        }

                        isFavoured = false;
//                        c.close();
                        add_bookmark.setChecked(isFavoured);


                    }

            });

        }

        return view;
    }

    private void saveImages() {

        Target target = new Target() {

            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getPosterPath());
                        Poster = file.getPath();
                        movie.setPosterPath(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getPosterPath());
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            movie.setBackdropPath(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getName());
                            ostream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
        Target target2 = new Target() {

            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getBackdropPath());
                        Log.i("file", file.getName());
                        movie.setBackdropPath(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getBackdropPath());

                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            movie.setBackdropPath(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getName());
                            ostream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }


            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };

        Picasso.get()
                .load(Constants.basePosterPath + movie.getPosterPath())
                .into(target);
        Picasso.get()
                .load(Constants.basePosterPath + movie.getBackdropPath())
                .into(target2);

    }


    private void initUI(View v) {
        rating = (TextView) v.findViewById(R.id.detail_rating);
        releaseDate = (TextView) v.findViewById(R.id.detail_release);
        overview = (TextView) v.findViewById(R.id.detail_overview);
        poster = (ImageView) v.findViewById(R.id.moviePoster);
        background = (ImageView) v.findViewById(R.id.poster);
        reviewsList = (RecyclerView) v.findViewById(R.id.list_item_reviews);
        trailersList = (RecyclerView) v.findViewById(R.id.list_item_trailers);

    }


    private void setMovieData(MovieDetailsResponse movie) {
        rating.setText(
                new StringBuilder()
                        .append(String.valueOf(movie.getVote_average()))
                        .append(getString(R.string.separator))
                        .append(movie.getVote_count())
                        .toString());

        overview.setText(movie.getOverview());

        releaseDate.setText(movie.getRelease_date());
        Log.e("background", movie.getBackdropPath());

        try {

            Picasso.get()
                    .load(Constants.basePosterPath + movie.getPosterPath())
                    .into(poster);
            Picasso.get()
                    .load(Constants.basePosterPath + movie.getBackdropPath())
                    .into(background);
        } catch (Exception e) {
            Log.e(getResources().getString(R.string.picasso_exception), e.getMessage());
        }
    }

    @Override
    public void getMovieDetails(MovieDetailsResponse response) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)
                view.findViewById(R.id.collapsingToolbarLayout);
        toolbarLayout.setTitle(response.getName());
        movie = response;
        setMovieData(response);
    }

    @Override
    public void onTrailersRetrieved(ArrayList<Trailer> trailers) {
        TrailersAdapter trailersAdapter;
        if (null != trailers) {
            trailersAdapter =
                    new TrailersAdapter(getActivity().getBaseContext(), trailers);
            trailersList.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
            trailersList.setAdapter(trailersAdapter);
        }
    }

    @Override
    public void onReviewsRetrieved(ArrayList<Review> reviews) {
        ReviewsAdapter reviewsAdapter;
        if (null != reviews) {
            reviewsAdapter =
                    new ReviewsAdapter(getActivity().getBaseContext(), reviews);
            reviewsList.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
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
