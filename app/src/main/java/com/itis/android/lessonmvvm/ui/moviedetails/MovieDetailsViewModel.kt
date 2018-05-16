package com.itis.android.lessonmvvm.ui.moviedetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.itis.android.lessonmvvm.api.service.MovieService
import com.itis.android.lessonmvvm.model.Response
import com.itis.android.lessonmvvm.model.api_response.genre.Genre
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(private val movieService: MovieService) : ViewModel() {

    fun getGenresList(ids: List<Int>): LiveData<Response<List<Genre>>>? {
        var genresLiveData: MutableLiveData<Response<List<Genre>>>? = MutableLiveData()
        movieService.getGenreList()
                .map { it.genres }
                .map { it.filter { ids.contains(it.id) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    val genresLiveDataImm = genresLiveData
                    genresLiveDataImm?.value = Response.success(it)
                    genresLiveData = genresLiveDataImm
                }, onError = {
                    val genresLiveDataImm = genresLiveData
                    genresLiveDataImm?.value = Response.error(it)
                    genresLiveData = genresLiveDataImm
                })
        return genresLiveData
    }
}