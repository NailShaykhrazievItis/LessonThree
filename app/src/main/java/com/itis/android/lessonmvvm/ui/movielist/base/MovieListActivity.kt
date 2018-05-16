package com.itis.android.lessonmvvm.ui.movielist.base

import android.app.ActivityOptions
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.itis.android.lessonmvvm.R
import com.itis.android.lessonmvvm.di.di
import com.itis.android.lessonmvvm.model.Response
import com.itis.android.lessonmvvm.model.api_response.movie.Movie
import com.itis.android.lessonmvvm.ui.ViewModelFactory
import com.itis.android.lessonmvvm.ui.base.BaseActivity
import com.itis.android.lessonmvvm.ui.moviedetails.MovieDetailsActivity
import com.itis.android.lessonmvvm.ui.movielist.MovieListAdapter
import com.itis.android.lessonmvvm.ui.movielist.MovieListViewModel
import com.itis.android.lessonmvvm.utils.ARG_MOVIE
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import org.kodein.di.generic.instance



abstract class MovieListActivity : BaseActivity() {

    private val viewModelFactory: ViewModelFactory by di.instance()
    protected lateinit var viewModel: MovieListViewModel
    private var adapter: MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_movie_list, content_container)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
        initRecycler()
        supportActionBar(tb_movie_list)
        observeMovieList()
        observeProgressBar()
        observeItemClick()
    }

    protected abstract fun getMoviesList(): LiveData<Response<List<Movie>>>?

    private fun observeMovieList() =
            getMoviesList()?.observe(this, Observer {
                when {
                    it?.data != null -> {
                        adapter?.updateData(it.data)
                    }
                    it?.error != null -> {
                        Snackbar.make(container, it.error.message
                                ?: "We have problem", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {
                        Snackbar.make(container, "We have problem!!!", Snackbar.LENGTH_SHORT).show()
                    }
                }
            })

    private fun observeSearchList(query: String) =
            viewModel.getMoviesByQuery(query)?.observe(this, Observer {
                when {
                    it?.data != null -> {
                        adapter?.updateData(it.data)
                    }
                    it?.error != null -> {
                        Snackbar.make(container, it.error.message
                                ?: "We have problem", Snackbar.LENGTH_SHORT).show()
                        observeMovieList()
                    }
                    else -> {
                        Snackbar.make(container, "We have problem!!!", Snackbar.LENGTH_SHORT).show()
                    }
                }
            })

    private fun observeProgressBar() =
            viewModel.isLoading().observe(this, Observer {
                if (it != null && it) {
                    pg_movies.visibility = View.VISIBLE
                } else {
                    pg_movies.visibility = View.GONE
                }
            })

    private fun observeItemClick() =
            viewModel.navigateToMovieDetails.observe(this, Observer {
                it?.let {
                    val intent = Intent(this, MovieDetailsActivity::class.java)
                    intent.putExtra(ARG_MOVIE, it.first)
                    val transitionName = getString(R.string.transaction_poster)
                    val transitionActivityOptions =
                            ActivityOptions.makeSceneTransitionAnimation(this, it.second, transitionName)
                    startActivity(intent, transitionActivityOptions.toBundle())
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

    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)

        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem.getActionView() as SearchView?
        }
        if (searchView != null) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    observeSearchList(query)
                    if (!searchView.isIconified) {
                        searchView.isIconified = true
                    }
                    searchItem.collapseActionView()
                    return false
                }

                override fun onQueryTextChange(s: String): Boolean {
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}
