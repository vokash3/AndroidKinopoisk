package ru.nitestalker.androidkinopoisk.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.nitestalker.androidkinopoisk.model.docs.Movie;
import ru.nitestalker.androidkinopoisk.retrofit.ApiFactory;

public class MainViewModel extends AndroidViewModel {

    private final String TAG = "MainViewModel";
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadMovies() {
        Disposable disposable = ApiFactory.apiService.loadMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                            List<Movie> loadedMovies = movies.getValue();
                            if(loadedMovies != null) {
                                loadedMovies.addAll(movieResponse.getMovies());
                                movies.setValue(loadedMovies);
                            } else movies.setValue(movieResponse.getMovies());
                            page++;
                        },
                        throwable -> Log.e(TAG, throwable.toString()));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return movies;
    }
}
