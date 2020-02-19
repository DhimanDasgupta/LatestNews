package com.dhimandasgupta.news.domain

import com.dhimandasgupta.news.data.models.NetworkResponse

fun NetworkResponse.toDomainModel(): NewsDomainModel {
    val articleDomainModels = mutableListOf<ArticleDomainModel>()
    this.articles.map { articleNetworkModel ->
        articleDomainModels.add(
            ArticleDomainModel(
                sourceName = articleNetworkModel.source.name,
                author = articleNetworkModel.author,
                title = articleNetworkModel.title,
                description = articleNetworkModel.description,
                url = articleNetworkModel.url,
                urlToImage = articleNetworkModel.urlToImage,
                publishedAt = articleNetworkModel.publishedAt,
                content = articleNetworkModel.content
            )
        )
    }
    return NewsDomainModel(articleDomainModels.toList())
}

