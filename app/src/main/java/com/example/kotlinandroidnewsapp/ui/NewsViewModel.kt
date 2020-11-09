package com.example.kotlinandroidnewsapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinandroidnewsapp.model.Article
import com.example.kotlinandroidnewsapp.repository.LocalRepository
import com.example.kotlinandroidnewsapp.repository.RemoteRepository
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(
    val remoteRepository: RemoteRepository,
    val localRepository: LocalRepository
): ViewModel() {

    val newsLiveData = MutableLiveData<MutableList<Article>>()
    val searchNewsLiveData = MutableLiveData<MutableList<Article>>()

    fun getBreakingNews() = viewModelScope.launch {
        val breakingNews = remoteRepository.getBreakingNews()
        newsLiveData.postValue(breakingNews)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        val searchNews = remoteRepository.searchNews(searchQuery)
        searchNewsLiveData.postValue(searchNews)
    }

    fun saveNews(article: Article) = viewModelScope.launch {
        localRepository.insertNewsToDb(article)
    }

    fun deleteNews(article: Article) = viewModelScope.launch {
        localRepository.deleteNews(article)
    }

    fun getSavedNews() = localRepository.getSavedNews()

}