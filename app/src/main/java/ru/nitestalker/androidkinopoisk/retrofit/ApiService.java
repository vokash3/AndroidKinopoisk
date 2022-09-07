package ru.nitestalker.androidkinopoisk.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.nitestalker.androidkinopoisk.model.MovieResponse;
import ru.nitestalker.androidkinopoisk.model.ReviewResponse;
import ru.nitestalker.androidkinopoisk.model.TrailersResponse;

public interface ApiService {

    @GET("movie?field=rating.kp&search=4-10&sortField=votes.imdb&sortType=-1&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06&limit=50")
    Single<MovieResponse> loadMovies(@Query("page") int page); // @Query - для динамичных запросов

    @GET("movie?token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06&field=id")
    Single<TrailersResponse> loadTrailers(@Query("search") int id);

    @GET("review?token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06&limit=30&sortField=date&sortType=-1&field=movieId")
    Single<ReviewResponse> loadReviews(@Query("search") int id);

}
