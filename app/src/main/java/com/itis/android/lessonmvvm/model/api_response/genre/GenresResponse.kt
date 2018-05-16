package com.itis.android.lessonmvvm.model.api_response.genre

import com.google.gson.annotations.SerializedName

data class GenresResponse(@SerializedName("genres") val genres: List<Genre>)

