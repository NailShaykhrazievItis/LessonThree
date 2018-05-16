package com.itis.android.lessonmvvm.ui.moviedetail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.itis.android.lessonmvvm.R
import com.itis.android.lessonmvvm.di.di
import com.itis.android.lessonmvvm.model.api_response.genre.Genre
import com.itis.android.lessonmvvm.model.api_response.movie.Movie
import com.itis.android.lessonmvvm.ui.ViewModelFactory
import com.itis.android.lessonmvvm.utils.ARG_MOVIE
import com.itis.android.lessonmvvm.utils.POSTER_SIZE_XLARGE
import com.itis.android.lessonmvvm.utils.loadPicture
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.layout_movie_details.*
import org.kodein.di.generic.instance

/**
 * Created by Nail Shaykhraziev on 06.05.2018.
 */
class MovieDetailsActivity : AppCompatActivity() {

    private val viewModelFactory: ViewModelFactory by di.instance()
    private lateinit var viewModel: MovieDetailsViewModel

    companion object {
        fun startActivity(activity: Activity, movie: Movie, bundle: Bundle) {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(ARG_MOVIE, movie)
            activity.startActivity(intent, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
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
        observeGenres(movie)
    }

    private fun observeGenres(movie: Movie) =
            viewModel.getGenres()?.observe(this, Observer {
                when {
                    it?.data != null -> {
                        val genres: List<Genre>? = it.data
                        genres?.forEach {
                            if (movie.genres?.contains(it.id) == true){
                                val text = tv_genres.text as? String + it.name + ", "
                                tv_genres.text = text
                            }
                        }
                        val text = tv_genres.text as? String
                        tv_genres.text = text?.substring(0, text.lastIndex - 1)
                    }
                    it?.error != null -> {
                        Snackbar.make(container, it.error.message
                                ?: "We have problem", Snackbar.LENGTH_SHORT)
                    }
                    else -> {
                        Snackbar.make(container, "We have problem!!!", Snackbar.LENGTH_SHORT)
                    }
                }
            })
}
