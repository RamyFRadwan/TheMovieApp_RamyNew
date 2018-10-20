package com.ramyfradwan.ramy.themovieapp_tmdb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Review> reviews;

    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_reviews, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviews.get(position);

        holder.review_author.setText(review.getAuthor());
        holder.review_content.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        if (null != reviews)
            return reviews.size();
        else {
            Toast.makeText(context, context.getString(R.string.error_retrieving_reviews), Toast.LENGTH_SHORT).show();
            return 0;

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView review_author;
        private TextView review_content;

        ViewHolder(View itemView) {
            super(itemView);
            review_author = itemView.findViewById(R.id.review_author);
            review_content = itemView.findViewById(R.id.review_content);
        }
    }
}