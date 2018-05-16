package com.itis.android.lessonmvvm.model.api_response.genre

import com.google.gson.annotations.SerializedName

data class Genre(@SerializedName("id")
                 val id: Int?,
                 @SerializedName("name")
                 val name: String)
