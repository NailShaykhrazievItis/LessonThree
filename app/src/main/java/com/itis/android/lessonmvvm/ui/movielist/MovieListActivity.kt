package com.itis.android.lessonmvvm.ui.movielist

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.itis.android.lessonmvvm.R
import com.itis.android.lessonmvvm.di.di
import com.itis.android.lessonmvvm.ui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import org.kodein.di.generic.instance

class MovieListActivity : AppCompatActivity() {

    private val viewModelFactory: ViewModelFactory by di.instance()
    private lateinit var viewModel: MovieListViewModel
    private var adapter: MovieListAdapter? = null
    private lateinit var bottomNavigationMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
        initRecycler()
        setSupportActionBar(tb_movie_list)
        bottomNavigationMenu = findViewById(R.id.bottom_navigation)
        bottomNavigationMenu.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener)
        observePopularMoviesList()
        observeProgressBar()
        observeItemClick()

    }

    private fun observePopularMoviesList() {
        viewModel.getPopularMoviesList()?.observe(this, Observer {
            when {
                it?.data != null -> {
                    adapter?.updateData(it.data)
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


    private fun observeProgressBar() =
            viewModel.isLoading().observe(this, Observer {
                if (it != null && it) {
                    pg_movies.visibility = View.VISIBLE
                } else {
                    pg_movies.visibility = View.GONE
                }
            })

    private fun observeTopRatedMoviesList() {
        viewModel.getTopRatedMoviesList()?.observe(this, Observer {
            when {
                it?.data != null -> {
                    adapter?.updateData(it.data)
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



    private fun observeItemClick() =
            viewModel.navigateToMovieDetails.observe(this, Observer {
                it?.let {
                    val transitionName = getString(R.string.transaction_poster)
                    val transitionActivityOptions =
                            ActivityOptions.makeSceneTransitionAnimation(this, it.second, transitionName)
                    com.itis.android.lessonmvvm.ui.moviedetail.MovieDetailsActivity.startActivity(this, it.first, transitionActivityOptions.toBundle())
                }
            })

    private fun initRecycler() {
        adapter = MovieListAdapter(ArrayList(0), { pair -> viewModel.movieClicked(pair) })
        val manager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(rv_movies.context, manager.orientation)
        rv_movies.layoutManager = manager
        rv_movies.addItemDecoration(dividerItemDecoration)
        rv_movies.adapter = adapter
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_popular -> {
                observePopularMoviesList()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_top -> {
                observeTopRatedMoviesList()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_search -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
