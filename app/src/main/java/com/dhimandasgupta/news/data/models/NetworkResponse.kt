package com.dhimandasgupta.news.data.models


import com.dhimandasgupta.news.data.models.Article
import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)