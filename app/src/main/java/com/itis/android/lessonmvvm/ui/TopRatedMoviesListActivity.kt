package com.itis.android.lessonmvvm.ui

import android.arch.lifecycle.LiveData
import com.itis.android.lessonmvvm.model.Response
import com.itis.android.lessonmvvm.model.api_response.movie.Movie
import com.itis.android.lessonmvvm.ui.movielist.MovieListActivity

class TopRatedMoviesListActivity : MovieListActivity() {

    override fun getMoviesList(): LiveData<Response<List<Movie>>>? {
        return viewModel.getTopRatedMoviesList()
    }
}
