package com.dhimandasgupta.news.domain

interface NewsRepository {
    suspend fun getEverythingByQuery(query: String): NewsDomainModel
}