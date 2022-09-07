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
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import ru.nitestalker.androidkinopoisk.model.ReviewResponse;
import ru.nitestalker.androidkinopoisk.model.TrailersResponse;
import ru.nitestalker.androidkinopoisk.model.json.Trailer;
import ru.nitestalker.androidkinopoisk.model.reviews.Review;
import ru.nitestalker.androidkinopoisk.retrofit.ApiFactory;

public class MovieDetailsViewModel extends AndroidViewModel {

    private final String TAG = "MovieDetailsViewModel";

    @Getter
    private MutableLiveData<List<Trailer>> listTrailers = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailersResponse, List<Trailer>>() { // Преобразуем TrailerResponse в List<Trailer>. Грубо говоря, распаковываем респонс и передаём далее по цепочке список из респонса

                    @Override
                    public List<Trailer> apply(TrailersResponse trailersResponse) throws Throwable {
                        return trailersResponse.getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailers) throws Throwable {
                        listTrailers.setValue(trailers);
                        Log.d(TAG, trailers.toString());
                    }
                }, throwable -> {
                    Log.e(TAG, throwable.toString());
                });
        compositeDisposable.add(disposable);
    }

    public void loadReviews(int id) {
        Disposable disposable = ApiFactory.apiService.loadReviews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewResponse, List<Review>>() { // Преобразуем TrailerResponse в List<Trailer>. Грубо говоря, распаковываем респонс и передаём далее по цепочке список из респонса
                    @Override
                    public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviews();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviews) throws Throwable {
                        Log.d(TAG, reviews.toString());
                    }
                }, throwable -> {
                    Log.e(TAG, throwable.toString());
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
