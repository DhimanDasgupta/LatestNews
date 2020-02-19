package com.dhimandasgupta.news.presentation

import androidx.lifecycle.*
import com.dhimandasgupta.news.domain.NewsUseCase
import com.dhimandasgupta.news.domain.Params
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val useCase: NewsUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val uiDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val mutableUIState = MutableLiveData<UIModels>().also {
        it.value = LoadingUIModel
    }
    val uiStateLiveData: LiveData<UIModels> = mutableUIState

    init {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                kotlin.runCatching {
                    return@runCatching useCase.getEverythingByQuery(Params("google")).toUIModel()
                }.onSuccess { uiModels ->
                    withContext(uiDispatcher) {
                        mutableUIState.postValue(uiModels)
                    }
                }.onFailure { throwable ->
                    withContext(Dispatchers.Main) {
                        mutableUIState.postValue(ErrorUIModel(throwable))
                    }
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(
    private val useCase: NewsUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val uiDispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(useCase, ioDispatcher, uiDispatcher) as T
        }
        throw IllegalAccessException("Please make sure that, you are passing tht correct parameters to create the ViewModel")
    }

}