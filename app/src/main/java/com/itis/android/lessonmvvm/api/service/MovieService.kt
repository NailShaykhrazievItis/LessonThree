package com.itis.android.lessonmvvm.api.service

import com.itis.android.lessonmvvm.model.api_response.genre.GenresResponse
import com.itis.android.lessonmvvm.model.api_response.movie.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nail Shaykhraziev on 29.04.2018.
 */
interface MovieService {

    @GET("movie/popular")
    fun popularMovies(): Single<MoviesResponse>

    @GET("movie/top_rated")
    fun topRatedMovies(): Single<MoviesResponse>

    @GET("search/movie")
    fun getMoviesByQuery(@Query("query") query: String): Single<MoviesResponse>

    @GET("genre/movie/list")
    fun getGenreList(): Single<GenresResponse>

}