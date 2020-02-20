package com.dhimandasgupta.news.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
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
    private val textView: AppCompatTextView = itemView.findViewById(R.id.adapter_article_text_view)

    fun bindArticle(articleUIModel: ArticleUIModel) {
        textView.text = articleUIModel.description
    }
}

class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindEmpty() {

    }
}