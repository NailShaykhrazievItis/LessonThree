package com.itis.android.lessonmvvm.model.api_response.movie

import com.google.gson.annotations.SerializedName

/**
 * Created by Nail Shaykhraziev on 29.04.2018.
 */
data class MoviesResponse(@SerializedName("results")
                          val movies: List<Movie>)
