package com.example.kotlinandroidnewsapp.repository

import com.example.kotlinandroidnewsapp.api.NewsAPI
import com.example.kotlinandroidnewsapp.model.Article
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    val newsAPI: NewsAPI
) : BaseRepository() {

    suspend fun getBreakingNews(): MutableList<Article>? {
        return safeApiCall(
            call = { newsAPI.getBreakingNews() },
            error = "Error fetching news"
        )?.articles?.toMutableList()
    }

    suspend fun searchNews(searchQuery: String): MutableList<Article>? {
        return safeApiCall(
            call = {newsAPI.searchNews(searchQuery)},
            error = "Error fetching news"
        )?.articles?.toMutableList()
    }
}