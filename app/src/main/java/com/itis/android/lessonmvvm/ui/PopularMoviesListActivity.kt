package com.itis.android.lessonmvvm.ui

import com.itis.android.lessonmvvm.ui.movielist.MovieListActivity

class PopularMoviesListActivity : MovieListActivity() {

    override fun getMoviesList() = viewModel.getPopularMoviesList()
}
