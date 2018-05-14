package com.itis.android.lessonmvvm.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.itis.android.lessonmvvm.api.service.MovieService
import com.itis.android.lessonmvvm.ui.moviedetails.MovieDetailsViewModel
import com.itis.android.lessonmvvm.ui.movielist.MovieListViewModel

/**
 * Created by Nail Shaykhraziev on 03.05.2018.
 */
class ViewModelFactory(private val movieService: MovieService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            when {
                modelClass.isAssignableFrom(MovieListViewModel::class.java) -> {
                    MovieListViewModel(movieService) as? T
                            ?: throw IllegalArgumentException("Unknown ViewModel class")
                }
                modelClass.isAssignableFrom(MovieDetailsViewModel::class.java) -> {
                    MovieDetailsViewModel(movieService) as? T
                            ?: throw IllegalArgumentException("Unknown ViewModel class")
                }
                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
}
