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
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import ru.nitestalker.androidkinopoisk.model.TrailersResponse;
import ru.nitestalker.androidkinopoisk.model.json.Trailer;
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
                .subscribe(new Consumer<TrailersResponse>() {
                    @Override
                    public void accept(TrailersResponse trailersResponse) throws Throwable {
                        listTrailers.setValue(trailersResponse.getTrailersList().getTrailers());
                        Log.d(TAG, trailersResponse.toString());
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
