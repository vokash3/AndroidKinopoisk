package ru.nitestalker.androidkinopoisk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nitestalker.androidkinopoisk.activities.FavouritesActivity;
import ru.nitestalker.androidkinopoisk.activities.MovieDetailsActivity;
import ru.nitestalker.androidkinopoisk.adapter.MoviesAdapter;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;
import ru.nitestalker.androidkinopoisk.viewmodel.MainViewModel;

// View - (V)
public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private MainViewModel viewModel;

    private RecyclerView recyclerViewMovies;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBarLoading;
    private int backPressedQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        // Установка адаптера в Recycler
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        moviesAdapter = new MoviesAdapter();
        recyclerViewMovies.setAdapter(moviesAdapter);
        //Задаём вид Recycler
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));
        // Инициализация уровня View-Model (VM)
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovieList(movies);
            }
        });
//        viewModel.loadMovies(); // перенесено в конструктор ViewModel
        // Активируем progressBar
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading)
                    progressBarLoading.setVisibility(View.VISIBLE);
                else progressBarLoading.setVisibility(View.GONE);
            }
        });
        // Активируем слушатель адаптера достижения конца списка
        moviesAdapter.setOnReachEndListener(new MoviesAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadMovies();
            }
        });

        // Реакция при нажатии на элемент списка
        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent movieDetailsActivity = MovieDetailsActivity.newIntent(MainActivity.this, movie);
                startActivity(movieDetailsActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (this.backPressedQ == 1) {
            this.backPressedQ = 0;
            super.onBackPressed();
        } else {
            this.backPressedQ++;
            Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT).show();
        }

        //Обнуление счётчика через 5 секунд
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressedQ = 0;
            }
        }, 5000);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
        return true; // Menu будет видно на экране. False - нет
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_item_fav_movies)
            startActivity(new Intent(FavouritesActivity.newIntent(this)));
        return super.onOptionsItemSelected(item);
    }
}