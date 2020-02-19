package com.dhimandasgupta.news.data

import com.dhimandasgupta.news.data.api.NewsApi
import com.dhimandasgupta.news.domain.NewsDomainModel
import com.dhimandasgupta.news.domain.NewsRepository
import com.dhimandasgupta.news.domain.toDomainModel

interface NewsService {
    suspend fun getEverythingByQuery(query: String): NewsDomainModel
}

class NewsServiceImpl(private val newsApi: NewsApi) : NewsService {
    override suspend fun getEverythingByQuery(query: String): NewsDomainModel {
        return newsApi.getEverythingByQuery(query).toDomainModel()
    }
}

class NewsRepositoryImpl(private val newsService: NewsService) : NewsRepository {
    override suspend fun getEverythingByQuery(query: String): NewsDomainModel {
        return newsService.getEverythingByQuery(query)
    }
}