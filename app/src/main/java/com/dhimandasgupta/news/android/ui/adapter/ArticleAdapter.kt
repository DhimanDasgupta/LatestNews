package com.dhimandasgupta.news.android.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.dhimandasgupta.news.R
import com.dhimandasgupta.news.presentation.ArticleUIModel

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val articleUIModels: MutableList<ArticleUIModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ArticleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_article,
                    parent,
                    false
                )
            )
            else -> EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.adapter_empty,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (articleUIModels.isEmpty()) {
            (holder as EmptyViewHolder).bindEmpty()
            return
        }

        when (articleUIModels[position]) {
            else -> (holder as ArticleViewHolder).bindArticle(articleUIModels[position])
        }
    }

    override fun getItemCount() = if (articleUIModels.isEmpty()) 1 else articleUIModels.size

    override fun getItemViewType(position: Int) = if (articleUIModels.isEmpty()) 1 else 0

    fun setItems(articleUIModels: List<ArticleUIModel>) {
        this.articleUIModels.clear()
        this.articleUIModels.addAll(articleUIModels)
        notifyDataSetChanged()
    }
}

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: AppCompatImageView =
        itemView.findViewById(R.id.adapter_article_image_view)
    private val textView: AppCompatTextView = itemView.findViewById(R.id.adapter_article_text_view)

    fun bindArticle(articleUIModel: ArticleUIModel) {
        if (articleUIModel.urlToImage.isNotBlank()) {
            imageView.load(articleUIModel.urlToImage) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                transformations(
                    RoundedCornersTransformation(
                        topLeft = 16f,
                        topRight = 16f,
                        bottomRight = 16f,
                        bottomLeft = 16f
                    )
                )
            }
        }
        textView.text = articleUIModel.title

        itemView.setOnClickListener {
            launchURL(articleUIModel.url)
        }
    }

    private fun launchURL(url: String) {
        kotlin.runCatching {
            itemView.context.startActivity(Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse(url)
            })
        }.onSuccess { }
            .onFailure { }
    }
}

class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindEmpty() {

    }
}