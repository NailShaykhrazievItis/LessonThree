package com.itis.android.lessonmvvm.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.itis.android.lessonmvvm.R
import com.itis.android.lessonmvvm.ui.movielist.PopularMoviesListActivity
import com.itis.android.lessonmvvm.ui.movielist.TopRatedMoviesListActivity
import kotlinx.android.synthetic.main.activity_base.*

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    protected fun supportActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        initNavigationDrawer(toolbar)
    }

    protected fun setBackArrow(toolbar: Toolbar) {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun initNavigationDrawer(toolbar: Toolbar) {
        nav_view.setNavigationItemSelectedListener{
            when (it.getItemId()) {
                R.id.menu_top_movie -> {
                    val intent = Intent(applicationContext, TopRatedMoviesListActivity::class.java)
                    startActivity(intent)
                    it.isChecked = true
                    drawer_layout.closeDrawers()
                }
                R.id.menu_popular_movie -> {
                    val intent = Intent(applicationContext, PopularMoviesListActivity::class.java)
                    startActivity(intent)
                    it.isChecked = true
                    drawer_layout.closeDrawers()
                }
            }
            true
        }
        setActionBar(toolbar)
    }

    private fun setActionBar(toolbar: Toolbar) {
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }
}