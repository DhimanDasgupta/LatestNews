package com.dhimandasgupta.news.domain

data class NewsDomainModel(
    val articles: List<ArticleDomainModel> = emptyList()
)

data class ArticleDomainModel(
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)