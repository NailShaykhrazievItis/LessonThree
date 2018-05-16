package com.itis.android.lessonmvvm.model.api_response.genre

import com.google.gson.annotations.SerializedName

/**
 * Created by Nail Shaykhraziev on 29.04.2018.
 */
data class Genre(@SerializedName("id")
                 val id: Int?,
                 @SerializedName("name")
                 val name: String)
