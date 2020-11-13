package com.example.kotlinandroidnewsapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kotlinandroidnewsapp.model.Article
import com.example.kotlinandroidnewsapp.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(
    val remoteRepository: RemoteRepository
): ViewModel() {

    private var currentQueryValue: String? = null

    private var currentResult: Flow<PagingData<Article>>? = null

    fun getBreakingNews(): Flow<PagingData<Article>> {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Article>> = remoteRepository.getBreakingNews().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
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