package ru.nitestalker.androidkinopoisk.adapter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.nitestalker.androidkinopoisk.R;
import ru.nitestalker.androidkinopoisk.model.reviews.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private final String TAG = "ReviewsAdapter";

    private List<Review> reviews = new ArrayList<>();
    @SuppressLint("NotifyDataSetChanged")
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.textViewReviewAuthorName.setText(review.getAuthor());
        holder.textViewDislikesCount.setText(review.getReviewLikes());
        holder.textViewLikesCount.setText(review.getReviewLikes());
        holder.textViewReviewTitle.setText(review.getTitle());
        holder.textViewCardReviewText.setText(review.getReview());
        DateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ");
        try {
            Date date = dateFormat.parse(review.getDate());
            holder.textViewReviewDate.setText(df.format(date));
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        switch (review.getType()){
            case "Позитивный":
                holder.constraintLayoutReview.setBackgroundResource(R.color.green);
                break;
            case "Негативный":
                holder.constraintLayoutReview.setBackgroundResource(R.color.red);
                break;
            default:
                holder.constraintLayoutReview.setBackgroundResource(R.color.grey);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout constraintLayoutReview;
        private final TextView textViewCardReviewText;
        private final TextView textViewReviewAuthorName;
        private final TextView textViewReviewDate;
        private final TextView textViewReviewTitle;
        private final TextView textViewLikesCount;
        private final TextView textViewDislikesCount;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutReview = itemView.findViewById(R.id.constraintLayoutReview);
            textViewCardReviewText = itemView.findViewById(R.id.textViewCardReviewText);
            textViewReviewAuthorName = itemView.findViewById(R.id.textViewReviewAuthorName);
            textViewReviewDate = itemView.findViewById(R.id.textViewReviewDate);
            textViewReviewTitle = itemView.findViewById(R.id.textViewReviewTitle);
            textViewLikesCount = itemView.findViewById(R.id.textViewLikesCount);
            textViewDislikesCount = itemView.findViewById(R.id.textViewDislikesCount);
        }
    }
}
