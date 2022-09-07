package ru.nitestalker.androidkinopoisk.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.nitestalker.androidkinopoisk.R;
import ru.nitestalker.androidkinopoisk.adapter.TrailersAdapter;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;
import ru.nitestalker.androidkinopoisk.model.json.Trailer;
import ru.nitestalker.androidkinopoisk.viewmodel.MovieDetailsViewModel;

public class MovieDetailsActivity extends AppCompatActivity {
    private final String TAG = "MovieDetailsActivity";

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewRatingStats;
    private TextView textViewReleaseDate;
    private TextView textViewDescription;

    private MovieDetailsViewModel viewModel;
    private TrailersAdapter trailersAdapter;
    private RecyclerView recyclerViewTrailers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        initViews();
        initAdapters();
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewBigPoster);
        textViewTitle.setText(movie.getName());
        textViewReleaseDate.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());
        textViewRatingStats.setText(String.valueOf(movie.getRating().getKp()));

        viewModel.loadTrailers(movie.getId()); // Загружаем трейлеры фильма
        viewModel.loadReviews(movie.getId()); // Загружаем отзывы к фильму
        viewModel.getListTrailers().observe(this, new Observer<List<Trailer>>() { // Подписываемся на изменения
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });

    }

    private void initAdapters() {
        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); // Переход по url для просмотра трейлера (НЕЯВНЫЙ ИНТЕНТ)
                startActivity(intentToTrailer);
            }
        });
    }

    private void initViews() {
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewDescription = findViewById(R.id.textViewOverview);
        textViewRatingStats = findViewById(R.id.textViewRatingStats);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra("movie", movie); // Для передачи кастомных классов они д.б. Serializable
        return intent;
    }
}