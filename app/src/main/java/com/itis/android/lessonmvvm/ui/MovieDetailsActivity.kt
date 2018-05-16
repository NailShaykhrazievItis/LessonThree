package com.itis.android.lessonmvvm.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.itis.android.lessonmvvm.R
import com.itis.android.lessonmvvm.model.api_response.movie.Movie
import com.itis.android.lessonmvvm.utils.ARG_MOVIE
import com.itis.android.lessonmvvm.utils.POSTER_SIZE_XLARGE
import com.itis.android.lessonmvvm.utils.loadPicture
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.layout_movie_details.*

/**
 * Created by Nail Shaykhraziev on 06.05.2018.
 */
class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        fun startActivity(activity: Activity, movie: Movie) {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(ARG_MOVIE, movie)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        val movie = intent.extras.getParcelable<Movie>(ARG_MOVIE)
        initToolbar()
        initViews(movie)
    }

    private fun initToolbar() {
        setSupportActionBar(tb_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        tb_movie.setNavigationOnClickListener { onBackPressed() }
        window.statusBarColor = resources.getColor(R.color.transparent)
    }

    private fun initViews(movie: Movie) {
        loadPicture(iv_movie, movie.posterPath, POSTER_SIZE_XLARGE)
        ct_movie.title = movie.title
        tv_description.text = movie.overview
        tv_rating.text = movie.voteAverage.toString()
        tv_vote_count.text = movie.voteCount.toString()
        tv_release_date.text = movie.releaseDate
    }


}
