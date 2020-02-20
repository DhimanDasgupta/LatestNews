package com.dhimandasgupta.news.presentation

import androidx.lifecycle.*
import com.dhimandasgupta.news.domain.NewsUseCase
import com.dhimandasgupta.news.domain.Params
import kotlinx.coroutines.launch

class NewsViewModel(
    private val useCase: NewsUseCase
) : ViewModel() {
    private val mutableUIState = MutableLiveData<UIModels>().also {
        it.value = LoadingUIModel
    }
    val uiStateLiveData: LiveData<UIModels> = mutableUIState

    init {
        refreshNews()
    }

    fun refreshNews() {
        mutableUIState.postValue(LoadingUIModel)
        viewModelScope.launch {
            kotlin.runCatching {
                return@runCatching useCase.getEverythingByQuery(Params("google")).toUIModel()
            }.onSuccess { uiModels ->
                mutableUIState.postValue(uiModels)
            }.onFailure { throwable ->
                mutableUIState.postValue(ErrorUIModel(throwable))
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(
    private val useCase: NewsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(useCase) as T
        }
        throw IllegalAccessException("Please make sure that, you are passing tht correct parameters to create the ViewModel")
    }

}