package com.example.kotlinandroidnewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.kotlinandroidnewsapp.R
import com.example.kotlinandroidnewsapp.model.Article

class NewsAdapter : PagingDataAdapter<Article, ViewHolder>(ARTICLE_COMPARATOR) {

//    val differ = AsyncListDiffer(this, ARTICLE_COMPARATOR)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return NewsViewHolder.create(parent)
    }

    fun getItemAtPosition(position: Int): Article {
        return getItem(position)!!
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleItem = getItem(position)
        if (articleItem != null) {
            (holder as NewsViewHolder).bind(articleItem)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(articleItem)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }



    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}