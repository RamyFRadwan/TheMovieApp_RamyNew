package com.ramyfradwan.ramy.themovieapp_tmdb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Trailer> trailers;

    public TrailersAdapter(Context context, ArrayList<Trailer> trailers) {
        this.context = context;
        this.trailers = trailers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_trailers, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Trailer trailer = trailers.get(position);
        viewHolder.trailer_title.setText(trailer.getName());
        viewHolder.trailer_title.setContentDescription(trailer.getName());
        Picasso.get()
                .load(context.getString(R.string.youtube_url) + trailer.getKey() + context.getString(R.string.thumb_url)) //
                .into(viewHolder.trailer_thumbnail);

    }

    @Override
    public int getItemCount() {
        if (null != trailers)
            return trailers.size();
        else {
            Toast.makeText(context, R.string.error_retrieving_trailers, Toast.LENGTH_SHORT).show();
            return 0;

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trailer_title;
        private ImageView trailer_thumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            trailer_title = itemView.findViewById(R.id.trailer_title);
            trailer_thumbnail = itemView.findViewById(R.id.trailer_thumbnail);
        }
    }
}