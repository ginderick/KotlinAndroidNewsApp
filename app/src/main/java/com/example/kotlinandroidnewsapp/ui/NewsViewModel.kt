package com.example.kotlinandroidnewsapp.ui

import android.app.DownloadManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kotlinandroidnewsapp.model.Article
import com.example.kotlinandroidnewsapp.data.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(
    val newsRepository: NewsRepository
) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentResult: Flow<PagingData<Article>>? = null

    fun getBreakingNews(): Flow<PagingData<Article>> {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Article>> =
            newsRepository.getBreakingNews().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

    fun getSavedNews(): Flow<PagingData<Article>> {
        return newsRepository.getSavedNews()
    }

    fun update(article: Article) = viewModelScope.launch {
        newsRepository.update(article.url)
    }

    fun saveNews(article: Article) = viewModelScope.launch {
        newsRepository.saveNews(article)
    }

    fun deleteSavedNews(article: Article) = viewModelScope.launch {
        newsRepository.deleteSavedNews(article)
    }

    fun searchNews(query: String): Flow<PagingData<Article>> {
        val lastResult = currentResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<Article>> = newsRepository.searchNews(query)
            .cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}



