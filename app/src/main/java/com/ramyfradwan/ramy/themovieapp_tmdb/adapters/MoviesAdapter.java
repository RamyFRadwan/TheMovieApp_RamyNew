package com.ramyfradwan.ramy.themovieapp_tmdb.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.florent37.picassopalette.PicassoPalette;
import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.ui.MovieDetailActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context context;
    private List<Movie> movies = new ArrayList<>();


    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public MoviesAdapter(Context context) {
        this.context = context;
    }


    class MoviesViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;
        private TextView movieName;
        private LinearLayout full_layout;
        protected LinearLayout movieHolder;
        protected LinearLayout movieTitleHolder;
        protected ToggleButton favouriteButton;
        private View v;
        public MoviesViewHolder(View itemView) {
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
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, final int position) {
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


        }catch (Exception e){
            Log.e(context.getString(R.string.picasso_exception) , e.getMessage());
        }

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, MovieDetailActivity.class);
                i.putExtra(Constants.ID, movies.get(position).getId());
                context.startActivity(i);
            }
        });

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

}
