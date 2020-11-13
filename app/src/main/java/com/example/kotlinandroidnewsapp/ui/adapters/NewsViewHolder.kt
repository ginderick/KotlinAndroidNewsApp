package com.example.kotlinandroidnewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinandroidnewsapp.R
import com.example.kotlinandroidnewsapp.model.Article
import kotlinx.android.synthetic.main.news_preview.view.*

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvSource: TextView = view.findViewById(R.id.tvSource)
    private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    private val tvDescription: TextView = view.findViewById(R.id.tvDescription)
    private val tvPublishedAt: TextView = view.findViewById(R.id.tvPublishedAt)
    private val ivArticleImage: ImageView = view.findViewById(R.id.ivArticleImage)

    private var onItemClickListener: ((Article) -> Unit)? = null

    private var article: Article? = null

//    init {
//        setOnItemClickListener {
//            onItemClickListener?.let {
//                it(article)
//            }
//
//            }
//        }

    fun bind(article: Article?) {
        if (article == null) {
            val resources = itemView.resources
            tvTitle.text = resources.getString(R.string.loading)
            tvSource.text = resources.getString(R.string.loading)
            tvDescription.visibility = View.GONE
            tvPublishedAt.visibility = View.GONE
        } else {
            showArticleDAta(article)
        }

    }

    private fun showArticleDAta(article: Article) {
        this.article = article
        tvTitle.text = article.title
        tvSource.text = article.source?.name
        tvDescription.text = article.description
        tvPublishedAt.text = article.publishedAt
        Glide.with(itemView)
                .load(article.urlToImage)
                .into(ivArticleImage)
    }



    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_preview, parent, false)
            return NewsViewHolder(view)
        }
    }
}















