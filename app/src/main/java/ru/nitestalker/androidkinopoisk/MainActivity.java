package ru.nitestalker.androidkinopoisk;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nitestalker.androidkinopoisk.adapter.MoviesAdapter;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;
import ru.nitestalker.androidkinopoisk.viewmodel.MainViewModel;

// View - (V)
public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private MainViewModel viewModel;

    private RecyclerView recyclerViewMovies;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        viewModel.loadMovies();


    }
}