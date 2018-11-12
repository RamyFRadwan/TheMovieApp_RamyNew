package com.ramyfradwan.ramy.themovieapp_tmdb.adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.florent37.picassopalette.PicassoPalette;
import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.db_provider.MovieContract;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.ui.FilmDetailActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.ui.FilmDetailFragment;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Activity activity;
    private Context context;
    private List<Movie> movies = new ArrayList<>();
    private boolean fav, mTwoPane;
    private  String Poster;


    public MoviesAdapter(@NonNull Activity activity, @NonNull Context context, @NonNull List<Movie> movies, boolean mTwoPane, boolean fav) {
        this.context = context;
        this.movies = movies;
        this.fav = fav;
        this.mTwoPane = mTwoPane;
        this.activity = activity;
    }

    public MoviesAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.movie_card_item, parent, false);

        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, final int position) {
        if (!fav) {
            holder.movieName.setText(movies.get(position).getOriginalTitle());
            try {


                Picasso.get()
                        .load(Constants.basePosterPath + movies.get(position).getPosterPath())
                        .into(holder.poster);

                Picasso.get()
                        .load(Constants.basePosterPath + movies.get(position).getPosterPath())
                        .into(holder.poster,
                                PicassoPalette
                                        .with((Constants.basePosterPath + movies.get(position).getPosterPath()),
                                                holder.poster)
                                        .use(PicassoPalette.Profile.VIBRANT)
                                        .intoBackground(holder.full_layout,
                                                PicassoPalette.Swatch.RGB)
                                        .intoTextColor(holder.movieName,
                                                PicassoPalette.Swatch.TITLE_TEXT_COLOR));


            } catch (Exception e) {
                Log.e(context.getString(R.string.picasso_exception), e.getMessage());
            }
        } else {

            holder.movieName.setText(movies.get(position).getTitle());
            try {


                Picasso.get()
                        .load(new File(movies.get(position).getPosterPath()))
                        .into(holder.poster,
                                PicassoPalette
                                        .with((movies.get(position).getPosterPath()),
                                                holder.poster)
                                        .use(PicassoPalette.Profile.VIBRANT)
                                        .intoBackground(holder.full_layout,
                                                PicassoPalette.Swatch.RGB)
                                        .intoTextColor(holder.movieName,
                                                PicassoPalette.Swatch.TITLE_TEXT_COLOR));


            } catch (Exception e) {
                Log.e(context.getString(R.string.picasso_exception), e.getMessage());
            }
            holder.favouriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        onChecked(b,position,holder);
                    }
            });

        }

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(FilmDetailFragment.ARG_ITEM_ID, movies.get(position).getId());
                    arguments.putSerializable(Constants.FILM, movies.get(position));
                    Bundle bundle = new Bundle();
                    bundle.putBundle(Constants.movie,arguments);
                    FilmDetailFragment fragment = new FilmDetailFragment();
                    fragment.setArguments(arguments);
                    activity.getFragmentManager().beginTransaction()
                            .replace(R.id.film_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(context, FilmDetailActivity.class);
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(Constants.FILM,movies.get(position));
                    arguments.putBoolean(Constants.Fav,fav);
                    arguments.putInt(Constants.ID,movies.get(position).getId());
                    intent.putExtras(arguments);
                    context.startActivity(intent);
                }
            }
        });

    }

    private void onChecked(boolean b, int position,  MoviesViewHolder holder) {
        //Saving images Locally
        saveImages(movies.get(position));

        // Adding Movie Values to DB
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movies.get(position).getId());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movies.get(position).getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movies.get(position).getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movies.get(position).getBackdropPath());
        values.put(MovieContract.MovieEntry.COLUMN_RATING, movies.get(position).getVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_DATE, movies.get(position).getReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_IMAGE, movies.get(position).getPosterPath());

        context.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);

        holder.favouriteButton.setChecked(b);
    }

    @Override
    public int getItemCount() {
        Log.i("Movies Count", movies.size() + "");
        return movies.size();
    }

    public void appendItems(List<Movie> items) {
        movies.addAll(items);
        notifyItemRangeInserted(getItemCount(), items.size());
    }

    public void clear() {
        movies.clear();
        notifyDataSetChanged();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout movieHolder;
        protected LinearLayout movieTitleHolder;
        protected ToggleButton favouriteButton;
        private ImageView poster;
        private TextView movieName;
        private LinearLayout full_layout;
        private View v;


         MoviesViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            poster = itemView.findViewById(R.id.moviePosterImg);
            movieName = itemView.findViewById(R.id.movieName);
            full_layout = (LinearLayout) v.findViewById(R.id.movie_layout);
            movieHolder = (LinearLayout) v.findViewById(R.id.movieHolder);
            movieTitleHolder = (LinearLayout) v.findViewById(R.id.movieTitleHolder);
            favouriteButton = (ToggleButton) v.findViewById(R.id.favouriteButton);

        }
    }

    private void saveImages(final Movie movie) {

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

}
