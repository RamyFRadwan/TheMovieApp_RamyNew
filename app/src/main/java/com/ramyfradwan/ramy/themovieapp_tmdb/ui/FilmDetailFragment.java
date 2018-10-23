package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
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
import android.widget.ToggleButton;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.ReviewsAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.TrailersAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.db_provider.DbHelper;
import com.ramyfradwan.ramy.themovieapp_tmdb.db_provider.MovieContract;
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
    private TextView rating, releaseDate, overview;
    private ImageView poster, background;
    private RecyclerView reviewsList, trailersList;
    private View view;
    private MovieDetailPresenter presenter;

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
        im_path = sh.getString("posterpath", "no");
        pd_path = sh.getString("backdroppath", "no");
        final DbHelper db = new DbHelper(getActivity());
        Bundle arguments = getArguments();

        int id;
        if (arguments != null) {
            hasArguments = true;
            id = arguments.getInt(Constants.ID);
            if (id != 0) {
                presenter.getMovieDetails(FilmDetailFragment.class.getSimpleName(), id);
            }
            if (arguments.getBoolean("twoPane")) {
                mTwoPane = true;

            } else {
//                setMovieData(movie, view);
                Configuration config = getActivity().getResources().getConfiguration();
                Toolbar toolbar;
//                if (getResources().getConfiguration().orientation
//                        == Configuration.ORIENTATION_PORTRAIT) {
//
//                    toolbar = (Toolbar) view.findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
//                    AppCompatActivity activity = (AppCompatActivity) getActivity();
//                    activity.setSupportActionBar(toolbar);
////                    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//                    CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)
//                            view.findViewById(R.id.collapsingToolbarLayout);
////                    toolbarLayout.setTitle(movie.getName());
//                    final ImageView header_imageView = (ImageView) view.findViewById(R.id.poster);
//                    if (!fav) {
//                        Picasso
//                                .get()
//                                .load(Constants.basePosterPath + movie.getBackdropPath())
//                                .into(header_imageView);
//                    } else if (fav) {
//                        Picasso
//                                .get()
//                                .load(new File(pd_path))
//                                .into(header_imageView);
//
//                        Log.v("ssssssssssss", "" + pd_path);
//                        Log.v("ssssssssssss", Environment.getExternalStorageDirectory().getPath() + "/" + movie.getBackdropPath());
//
//                    }
//                }
//                toolbar = (Toolbar) view.findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
//                AppCompatActivity activity = (AppCompatActivity) getActivity();
//                activity.setSupportActionBar(toolbar);
//                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            assert movie != null;


//            isFavoured = Utility.isFavoured(movie.getId());
//            setMovieData(movie, view);

            final ToggleButton add_bookmark = (ToggleButton) view.findViewById(R.id.favouriteButton);
            add_bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                    if (isChecked) {
                        Target target = new Target() {

                            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {

                                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getPosterPath());
                                        Poster = file.getPath();
                                        movie.setPosterPath(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getPosterPath());
                                        Log.i("ff", file.getPath());
                                        try {
                                            file.createNewFile();
                                            FileOutputStream ostream = new FileOutputStream(file);
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
//                                movie.setBackdrop(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getTitle());
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


//                        db.getWritableDatabase();
                        String[] columns = {MovieContract.MovieEntry.COLUMN_TITLE};
                        String[] args = {movie.getName()};
                        Cursor c = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, columns, "title=?", args, null);
                        if (c.getCount() > 0 && c != null) {
//                            final String[] SELCTION_COLUMNS = {
//                                    Movie.getKeyTitle(),
//                            };
//                            int row = getActivity().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, "title=?", SELCTION_COLUMNS);
//                            if (row > 0) {
//                                Toast.makeText(getActivity(), R.string.delete_Success, Toast.LENGTH_SHORT).show();
//                                toggleButton.setVisibility(View.INVISIBLE);
//                            }

                            isFavoured = false;
                            add_bookmark.setText("OFF");
                        } else {
                            ContentValues values = new ContentValues();
                            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
                            values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getName());
                            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movie.getBackdropPath());
                            values.put(MovieContract.MovieEntry.COLUMN_RATING, movie.getVote_average());
                            values.put(MovieContract.MovieEntry.COLUMN_DATE, movie.getRelease_date());
                            values.put(MovieContract.MovieEntry.COLUMN_IMAGE, movie.getPosterPath());

                            Log.v("Imageessssss", "Image" + movie.getPosterPath() + ",,,,, backdrop_path" + movie.getBackdropPath());
                            getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);

                            isFavoured = true;
                            add_bookmark.setText(R.string.on);
                        }

                        add_bookmark.setChecked(isFavoured);
                    }
                }

            });

        }

        return view;
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
        Log.e("background", Constants.basePosterPath + movie.getBackdropPath());

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
