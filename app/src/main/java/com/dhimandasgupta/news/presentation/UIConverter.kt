package com.dhimandasgupta.news.presentation

import com.dhimandasgupta.news.domain.NewsDomainModel

fun NewsDomainModel.toUIModel(): UIModels {
    val articleUIModels = mutableListOf<ArticleUIModel>()

    this.articles.map { articleDomainModel ->
        articleUIModels.add(
            ArticleUIModel(
                sourceName = articleDomainModel.sourceName,
                author = articleDomainModel.author,
                title = articleDomainModel.title,
                description = articleDomainModel.description,
                url = articleDomainModel.url,
                urlToImage = articleDomainModel.urlToImage,
                publishedAt = articleDomainModel.publishedAt,
                content = articleDomainModel.content
            )
        )
    }

    return SuccessUIModel(ArticlesUIModel(articleUIModels.toList()))
}