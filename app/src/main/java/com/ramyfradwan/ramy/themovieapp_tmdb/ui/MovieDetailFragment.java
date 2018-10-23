//package com.ramyfradwan.ramy.themovieapp_tmdb.ui;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.annotation.Nullable;
//import android.support.design.widget.CollapsingToolbarLayout;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.ToggleButton;
//
//import com.ramyfradwan.ramy.themovieapp_tmdb.R;
//import com.ramyfradwan.ramy.themovieapp_tmdb.db_provider.MovieContract;
//import com.ramyfradwan.ramy.themovieapp_tmdb.model.MovieDetailsResponse;
//import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//
//import java.io.File;
//import java.io.FileOutputStream;
//
//public class MovieDetailFragment extends Fragment implements AdapterView.OnItemClickListener {
//
//    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();
//    private boolean hasArguments;
//    private MovieDetailsResponse movie;
//    private boolean isFavoured;
//    private MenuItem menuItem;
//    private boolean trailerFound;
//    public static String Poster;
//    private boolean mTwoPane;
//    private SharedPreferences sh;
//    private String im_path;
//    private String pd_path;
//    private boolean fav;
//
//    public MovieDetailFragment() {
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_detail, container, false);
//        sh = view.getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
//        im_path = sh.getString("posterpath", "no");
//        pd_path = sh.getString("backdroppath", "no");
////        final DbHelper db = new DbHelper(getActivity());
//        Bundle arguments = getArguments();
//
//        if (arguments != null) {
//            hasArguments = true;
//            if (arguments.getBoolean("twoPane")) {
//                mTwoPane = true;
////                setMovieData(movie, view);
//
//            } else {
////                setMovieData(movie, view);
//                Configuration config = getActivity().getResources().getConfiguration();
//                Toolbar toolbar;
//                if (getResources().getConfiguration().orientation
//                        == Configuration.ORIENTATION_PORTRAIT) {
//
//                    toolbar = (Toolbar) view.findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
//                    AppCompatActivity activity = (AppCompatActivity) getActivity();
//                    activity.setSupportActionBar(toolbar);
//                    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//                    CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)
//                            view.findViewById(R.id.collapsingToolbarLayout);
//                    toolbarLayout.setTitle(movie.getName());
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
////
////                    Picasso
////                            .with(getActivity())
////                            .load(movie.getPosterURI("w500", "backdrop"))
////                            .into(header_imageView);
////
//                }
//                toolbar = (Toolbar) view.findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
//                AppCompatActivity activity = (AppCompatActivity) getActivity();
//                activity.setSupportActionBar(toolbar);
//                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            }
//            assert movie != null;
//
//
////            isFavoured = Utility.isFavoured(movie.getId());
////            setMovieData(movie, view);
//
//            final ToggleButton add_bookmark = (ToggleButton) view.findViewById(R.id.favouriteButton);
//            add_bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
//                    if (isChecked) {
//                        Target target = new Target() {
//
//                            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                                new Thread(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//
//                                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getPosterPath());
//                                        Poster = file.getPath();
//                                        movie.setPosterPath(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getPosterPath());
//                                        Log.i("ff", file.getPath());
//                                        try {
//                                            file.createNewFile();
//                                            FileOutputStream ostream = new FileOutputStream(file);
//                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
////                                movie.setBackdrop(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getTitle());
//                                            ostream.close();
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                }).start();
//                            }
//
//                            @Override
//                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                            }
//
//                            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                                if (placeHolderDrawable != null) {
//                                }
//                            }
//                        };
//                        Target target2 = new Target() {
//
//                            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                                new Thread(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//
//                                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getBackdropPath());
//                                        Log.i("file", file.getName());
//                                        movie.setBackdropPath(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getBackdropPath());
//
//                                        try {
//                                            file.createNewFile();
//                                            FileOutputStream ostream = new FileOutputStream(file);
//                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
//                                            movie.setBackdropPath(Environment.getExternalStorageDirectory().getPath() + "/" + movie.getName());
//                                            ostream.close();
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                }).start();
//                            }
//
//                            @Override
//                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                            }
//
//
//                            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                                if (placeHolderDrawable != null) {
//                                }
//                            }
//                        };
//
//                        Picasso.get()
//                                .load(Constants.basePosterPath + movie.getPosterPath())
//                                .into(target);
//                        Picasso.get()
//                                .load(Constants.basePosterPath + movie.getBackdropPath())
//                                .into(target2);
//
//
////                        db.getWritableDatabase();
//                        String[] columns = {MovieContract.MovieEntry.COLUMN_TITLE};
//                        String[] args = {movie.getName()};
//                        Cursor c = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, columns, "title=?", args, null);
//                        if (c.getCount() > 0 && c != null) {
////                            final String[] SELCTION_COLUMNS = {
////                                    Movie.getKeyTitle(),
////                            };
////                            int row = getActivity().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, "title=?", SELCTION_COLUMNS);
////                            if (row > 0) {
////                                Toast.makeText(getActivity(), R.string.delete_Success, Toast.LENGTH_SHORT).show();
////                                toggleButton.setVisibility(View.INVISIBLE);
////                            }
//
//                            isFavoured = false;
//                            add_bookmark.setText("OFF");
//                        } else {
//                            ContentValues values = new ContentValues();
//                            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
//                            values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getName());
//                            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
//                            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movie.getBackdropPath());
//                            values.put(MovieContract.MovieEntry.COLUMN_RATING, movie.getVote_average());
//                            values.put(MovieContract.MovieEntry.COLUMN_DATE, movie.getRelease_date());
//                            values.put(MovieContract.MovieEntry.COLUMN_IMAGE, movie.getPosterPath());
//
//                            Log.v("Imageessssss", "Image" + movie.getPosterPath() + ",,,,, backdrop_path" + movie.getBackdropPath());
//                            getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
//
//                            isFavoured = true;
//                            add_bookmark.setText(R.string.on);
//                        }
//
//                        add_bookmark.setChecked(isFavoured);
//                    }
//                }
//
//            });
//
//        }
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
////        if (isAdded()) {
////            if (isFavoured && !Utility.isNetworkAvailable()) {
////                fetchOfflineTrailers();
////                fetchOfflineReviews();
////            } else
////        if (hasArguments) {
////            fetchTrailersTask fetchTrailersTask = new fetchTrailersTask();
////            fetchTrailersTask.execute();
////            fetchReviewsTask fetchReviewsTask = new fetchReviewsTask();
////            fetchReviewsTask.execute();
////
////
////        }
////        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_detail, menu);
//        menuItem = menu.findItem(R.id.shareAction);
//        menuItem.setVisible(trailerFound);
//    }
//
//    private Intent createShareIntent(String movie_name, String trailer_name, String key) {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.EXTRA_TEXT,
//                getString(R.string.Watch) + movie_name + getString(R.string.dots) + trailer_name
//                        + getString(R.string.youtubeUrl)
//                        + key + getString(R.string.Hash_KEY));
//        return shareIntent;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        getActivity().invalidateOptionsMenu();
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                getActivity().supportFinishAfterTransition();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
////      TODO:: Add share fix here
////    private void prepareShare() {
////        ShareActionProvider mShareActionProvider = new ShareActionProvider(getActivity());
////        MenuItemCompat.setActionProvider(menuItem, mShareActionProvider);
////        if (trailer_title != null && trailer_key != null) {
////            trailerFound = true;
////            mShareActionProvider.setShareIntent(createShareIntent(movie.getName(),
////                    trailer_title, trailer_key));
////        } else {
////            trailerFound = false;
////        }
////    }
//
////    private void setMovieData(Movie movie, View view) {
////        ImageView poster_imageView = (ImageView) view.findViewById(R.id.moviePoster);
////        if (MainActivityFragment.getItem() == false) {
////            Picasso
////                    .with(getActivity())
////                    .load(movie.getPosterURI("w500", "poster"))
////                    .into(poster_imageView);
////        } else {
////            Picasso
////                    .with(getActivity())
////                    .load(new File(im_path))
////                    .into(poster_imageView);
////            Log.v("qqqqqqqqqqqqqqweqwe", Environment.getExternalStorageDirectory().getPath() + "/" + movie.getBackdrop_path());
////
////        }
////
////        if (mTwoPane) {
////            TextView movie_title = (TextView) view.findViewById(R.id.movieTitle);
////            movie_title.setText(movie.getTitle());
////            movie_title.setContentDescription(movie.getTitle());
////        }
////
////        String release_date = movie.getRelease_date();
////        if (release_date.contains("-")) {
////            release_date = release_date.split("-")[0];
////        }
////
////        TextView release_year = (TextView) view.findViewById(R.id.detail_release);
////        release_year.setText(release_date);
////        release_year.setContentDescription("Release Date " + release_date);
////
////        TextView vote = (TextView) view.findViewById(R.id.detail_rating);
////        vote.setText(String.format("%.1f", movie.getVote_average()) + "/10");
////        vote.setContentDescription("Rating " + movie.getVote_average() + "/ 10");
////
////        TextView overview = (TextView) view.findViewById(R.id.detail_overview);
////        overview.setText(movie.getOverview());
////        overview.setContentDescription(movie.getOverview());
////
////    }
////
//
////    private void addReviewsToUI(List<Review> favouriteReviews) {
////        if (favouriteReviews.size() > 0) {
////            if (isAdded()) {
////                TextView reviews_title = (TextView) getActivity().findViewById(R.id.reviews_title);
////                reviews_title.setVisibility(View.VISIBLE);
////                mReviews = new ArrayList<>();
////                mReviews.addAll(favouriteReviews);
////                ReviewsAdapter mReviewsAdapter = new ReviewsAdapter(getActivity(), mReviews);
////                LinearLayout reviewsListView = (LinearLayout) getActivity()
////                        .findViewById(R.id.list_item_reviews);
////                for (int i = 0; i < mReviewsAdapter.getCount(); i++) {
////                    View view = mReviewsAdapter.getView(i, null, null);
////                    reviewsListView.addView(view);
////                }
////            }
////        }
////    }
////
////    private void addTrailersToUI(List<Trailer> trailers) {
////        if (trailers.size() > 0) {
////            if (isAdded()) {
////                TextView trailers_title = (TextView) getActivity().findViewById(R.id.trailers_title);
////                trailers_title.setVisibility(View.VISIBLE);
////                final ArrayList<Trailer> mTrailers = new ArrayList<>();
////                mTrailers.addAll(trailers);
////                TrailersAdapter mTrailersAdapter = new TrailersAdapter(getActivity(), mTrailers);
////                LinearLayout trailersListView = (LinearLayout) getActivity().findViewById(R.id.list_item_trailers);
////
////                for (int i = 0; i < mTrailersAdapter.getCount(); i++) {
////                    View view = mTrailersAdapter.getView(i, null, null);
////                    final int finalI = i;
////                    view.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            String youtubeLink = "https://www.youtube.com/watch?v=" + mTrailers.get(finalI).getKey();
////                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));
////                            Utility.preferPackageForIntent(getActivity(), intent,
////                                    Utility.YOUTUBE_PACKAGE_NAME);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
////                            startActivity(intent);
////                        }
////                    });
////                    trailersListView.addView(view);
////                    trailer_title = trailers.get(0).getName();
////                    trailer_key = trailers.get(0).getKey();
////                }
////            }
////        }
////    }
//
//    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
//    }
//
//}
//
//
