package com.itis.android.lessonmvvm.ui.movielist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.MainThread
import android.view.View
import com.itis.android.lessonmvvm.api.service.MovieService
import com.itis.android.lessonmvvm.model.Response
import com.itis.android.lessonmvvm.model.api_response.movie.Movie
import com.itis.android.lessonmvvm.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Nail Shaykhraziev on 29.04.2018.
 */
class MovieListViewModel(private val movieService: MovieService) : ViewModel() {

    private val loadingLiveData = MutableLiveData<Boolean>()
    private var moviesLiveData: MutableLiveData<Response<List<Movie>>>? = null

    val navigateToMovieDetails = SingleLiveEvent<Pair<Movie, View>>()

    fun isLoading(): LiveData<Boolean> {
        return loadingLiveData
    }

    fun movieClicked(movie: Pair<Movie, View>) {
        navigateToMovieDetails.value = movie
    }

    @MainThread
    fun getTopRatedMoviesList(): LiveData<Response<List<Movie>>>? {
        if (moviesLiveData == null) {
            moviesLiveData = MutableLiveData()
            movieService.topRatedMovies()
                    .map { it.movies }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { loadingLiveData.setValue(true) }
                    .doAfterTerminate { loadingLiveData.setValue(false) }
                    .subscribeBy(onSuccess = {
                        val moviesLiveDataImm = moviesLiveData
                        moviesLiveDataImm?.value = Response.success(it)
                        moviesLiveData = moviesLiveDataImm
                    }, onError = {
                        val moviesLiveDataImm = moviesLiveData
                        moviesLiveDataImm?.value = Response.error(it)
                        moviesLiveData = moviesLiveDataImm
                    })
        }
        return moviesLiveData
    }

    @MainThread
    fun getPopularMoviesList(): LiveData<Response<List<Movie>>>? {
        if (moviesLiveData == null) {
            moviesLiveData = MutableLiveData()
            movieService.popularMovies()
                    .map { it.movies }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { loadingLiveData.setValue(true) }
                    .doAfterTerminate { loadingLiveData.setValue(false) }
                    .subscribeBy(onSuccess = {
                        val moviesLiveDataImm = moviesLiveData
                        moviesLiveDataImm?.value = Response.success(it)
                        moviesLiveData = moviesLiveDataImm
                    }, onError = {
                        val moviesLiveDataImm = moviesLiveData
                        moviesLiveDataImm?.value = Response.error(it)
                        moviesLiveData = moviesLiveDataImm
                    })
        }
        return moviesLiveData
    }

    @MainThread
    fun getMoviesByQuery(query: String): LiveData<Response<List<Movie>>>? {
        movieService.getMoviesByQuery(query)
                .map { it.movies }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.setValue(true) }
                .doAfterTerminate { loadingLiveData.setValue(false) }
                .subscribeBy(onSuccess = {
                    val moviesLiveDataImm = moviesLiveData
                    moviesLiveDataImm?.value = Response.success(it)
                    moviesLiveData = moviesLiveDataImm
                }, onError = {
                    val moviesLiveDataImm = moviesLiveData
                    moviesLiveDataImm?.value = Response.error(it)
                    moviesLiveData = moviesLiveDataImm
                })
        return moviesLiveData
    }
}
