package com.example.kotlinandroidnewsapp.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter


class ArticlesLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ArticlesLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ArticlesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ArticlesLoadStateViewHolder {
        return ArticlesLoadStateViewHolder.create(parent, retry)
    }
}