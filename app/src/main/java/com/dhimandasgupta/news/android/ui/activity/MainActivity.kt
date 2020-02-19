package com.dhimandasgupta.news.android.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhimandasgupta.news.R
import com.dhimandasgupta.news.di.Generators
import com.dhimandasgupta.news.presentation.ErrorUIModel
import com.dhimandasgupta.news.presentation.LoadingUIModel
import com.dhimandasgupta.news.presentation.NewsViewModel
import com.dhimandasgupta.news.presentation.SuccessUIModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsViewModelFactory = Generators.provideNewsViewModelFactory()
        val mainActivityViewModel = ViewModelProvider(
            viewModelStore,
            newsViewModelFactory
        ).get(NewsViewModel::class.java)
        mainActivityViewModel.uiStateLiveData.observe(this, Observer { uiState ->
            uiState?.let { nonNullUiState ->
                when (nonNullUiState) {
                    is LoadingUIModel -> showLoading()
                    is ErrorUIModel -> showError(nonNullUiState)
                    is SuccessUIModel -> showSuccess(nonNullUiState)
                }
            }
        })
    }

    private fun showLoading() {
        Toast.makeText(this, "Show Loading", Toast.LENGTH_SHORT).show()
    }

    private fun showError(errorUIModel: ErrorUIModel) {
        Toast.makeText(
            this,
            "Show Error ${errorUIModel.throwable.localizedMessage}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showSuccess(successUIModel: SuccessUIModel) {
        Toast.makeText(
            this,
            "Show Success ${successUIModel.articlesUIModel.articles}",
            Toast.LENGTH_SHORT
        ).show()
    }
}
