package com.itis.android.lessonmvvm.ui.movielist

import android.arch.lifecycle.LiveData
import com.itis.android.lessonmvvm.model.Response
import com.itis.android.lessonmvvm.model.api_response.movie.Movie
import com.itis.android.lessonmvvm.ui.movielist.base.MovieListActivity

class TopRatedMoviesListActivity : MovieListActivity() {

    override fun getMoviesList():
            LiveData<Response<List<Movie>>>? = viewModel.getTopRatedMoviesList()
}