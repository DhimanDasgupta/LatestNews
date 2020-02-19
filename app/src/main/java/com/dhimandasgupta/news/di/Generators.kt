package com.dhimandasgupta.news.di

import com.dhimandasgupta.news.data.NewsRepositoryImpl
import com.dhimandasgupta.news.data.NewsService
import com.dhimandasgupta.news.data.NewsServiceImpl
import com.dhimandasgupta.news.data.api.NewsApi
import com.dhimandasgupta.news.data.api.NewsApiGenerator
import com.dhimandasgupta.news.domain.NewsRepository
import com.dhimandasgupta.news.domain.NewsUseCase
import com.dhimandasgupta.news.presentation.NewsViewModelFactory
import kotlinx.coroutines.Dispatchers

object Generators {
    fun provideNewsViewModelFactory(): NewsViewModelFactory {
        return NewsViewModelFactory(
            provideNewsUseCase(),
            provideIODispatcher(),
            provideUIDispatcher()
        )
    }

    private fun provideNewsUseCase(): NewsUseCase {
        return NewsUseCase(provideNewsRepository())
    }

    private fun provideNewsRepository(): NewsRepository {
        return NewsRepositoryImpl(provideNewsService())
    }

    private fun provideNewsService(): NewsService {
        return NewsServiceImpl(provideNewsApi())
    }

    private fun provideNewsApi(): NewsApi {
        return NewsApiGenerator.getNewsApi()
    }

    private fun provideIODispatcher() = Dispatchers.IO

    private fun provideUIDispatcher() = Dispatchers.Main
}