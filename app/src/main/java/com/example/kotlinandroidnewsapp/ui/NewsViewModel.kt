package com.example.kotlinandroidnewsapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kotlinandroidnewsapp.model.Article
import com.example.kotlinandroidnewsapp.data.NewsRepository
import com.example.kotlinandroidnewsapp.data.SavedNewsRepository
import kotlinx.coroutines.flow.Flow

class NewsViewModel @ViewModelInject constructor(
    val newsRepository: NewsRepository,
    val savedNewsRepository: SavedNewsRepository
): ViewModel() {

    private var currentQueryValue: String? = null

    private var currentResult: Flow<PagingData<Article>>? = null

    fun getBreakingNews(): Flow<PagingData<Article>> {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Article>> = newsRepository.getBreakingNews().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

    fun getSavedNews(): Flow<PagingData<Article>> {
//        val lastResult = currentResult
//        if (lastResult != null) {
//            return lastResult
//        }
        //        currentResult = newResult
        return savedNewsRepository.getSavedNews()
    }



//    fun searchNews(searchQuery: String) = viewModelScope.launch {
//        val searchNews = remoteRepository.searchNews(searchQuery)
//        searchNewsLiveData.postValue(searchNews)
//    }
//
//    fun saveNews(article: Article) = viewModelScope.launch {
//        localRepository.insertNewsToDb(article)
//    }
//
//    fun deleteNews(article: Article) = viewModelScope.launch {
//        localRepository.deleteNews(article)
//    }
//
//    fun getSavedNews() = localRepository.getSavedNews()

}