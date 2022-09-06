package ru.nitestalker.androidkinopoisk.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.nitestalker.androidkinopoisk.R;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;
import ru.nitestalker.androidkinopoisk.viewmodel.MainViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewRatingStats;
    private TextView textViewReleaseDate;
    private TextView textViewDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        initViews();

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewBigPoster);
        textViewTitle.setText(movie.getName());
        textViewReleaseDate.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());
        textViewRatingStats.setText(String.valueOf(movie.getRating().getKp()));
    }

    private void initViews() {
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewDescription = findViewById(R.id.textViewOverview);
        textViewRatingStats = findViewById(R.id.textViewRatingStats);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra("movie", movie); // Для передачи кастомных классов они д.б. Serializable
        return intent;
    }
}