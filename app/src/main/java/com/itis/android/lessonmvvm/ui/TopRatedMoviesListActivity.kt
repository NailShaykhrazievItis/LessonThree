package com.itis.android.lessonmvvm.ui

import com.itis.android.lessonmvvm.ui.movielist.MovieListActivity

class TopRatedMoviesListActivity : MovieListActivity() {

    override fun getMoviesList() = viewModel.getTopRatedMoviesList()
}
