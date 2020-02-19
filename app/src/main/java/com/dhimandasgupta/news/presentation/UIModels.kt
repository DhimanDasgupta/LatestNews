package com.dhimandasgupta.news.presentation

sealed class UIModels

object LoadingUIModel : UIModels()

data class ErrorUIModel(val throwable: Throwable) : UIModels()

data class SuccessUIModel(val articlesUIModel: ArticlesUIModel) : UIModels()

data class ArticlesUIModel(val articles: List<ArticleUIModel> = emptyList())

data class ArticleUIModel(
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)