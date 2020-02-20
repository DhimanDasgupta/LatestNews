package com.dhimandasgupta.news.android.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dhimandasgupta.news.R
import com.dhimandasgupta.news.android.ui.adapter.ArticleAdapter
import com.dhimandasgupta.news.databinding.ActivityMainBinding
import com.dhimandasgupta.news.di.Generators
import com.dhimandasgupta.news.presentation.ErrorUIModel
import com.dhimandasgupta.news.presentation.LoadingUIModel
import com.dhimandasgupta.news.presentation.NewsViewModel
import com.dhimandasgupta.news.presentation.SuccessUIModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupViews()
    }

    private fun setupViews() {
        // Setting Swipe Refresh Layout
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            if (binding.swipeRefreshLayout.isRefreshing) {
                return@setOnRefreshListener
            }
            newsViewModel.refreshNews()
        }

        // Setting Recycler View
        articleAdapter = ArticleAdapter()
        binding.recyclerView.layoutManager =
            GridLayoutManager(
                binding.recyclerView.context,
                resources.getInteger(R.integer.number_of_columns)
            )
        binding.recyclerView.adapter = articleAdapter
    }

    private fun setupViewModel() {
        val newsViewModelFactory = Generators.provideNewsViewModelFactory()
        newsViewModel = ViewModelProvider(
            viewModelStore,
            newsViewModelFactory
        ).get(NewsViewModel::class.java)
        newsViewModel.uiStateLiveData.observe(this, Observer { uiState ->
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
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun showError(errorUIModel: ErrorUIModel) {
        binding.swipeRefreshLayout.isRefreshing = false
        Snackbar.make(
            binding.root,
            errorUIModel.throwable.localizedMessage?.let {
                it
            } ?: "Something went wrong",
            Snackbar.LENGTH_LONG
        )
            .setAction("Retry") {
                newsViewModel.refreshNews()
            }.setActionTextColor(Color.RED).also {
                it.show()
            }
    }

    private fun showSuccess(successUIModel: SuccessUIModel) {
        binding.swipeRefreshLayout.isRefreshing = false
        articleAdapter.setItems(successUIModel.articlesUIModel.articles)
    }
}
