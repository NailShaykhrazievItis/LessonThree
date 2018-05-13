package com.itis.android.lessonmvvm.model.api_response

import com.google.gson.annotations.SerializedName

data class GenresResponse(@SerializedName("genres")
                          val genres: List<Genre>)