package ru.nitestalker.androidkinopoisk.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.nitestalker.androidkinopoisk.model.MovieResponse;

public interface ApiService {

    @GET("movie?field=rating.kp&search=4-10&sortField=votes.imdb&sortType=-1&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06&limit=200")
    Single<MovieResponse> loadMovies(@Query("page") int page); // @Query - для динамичных запросов

}
