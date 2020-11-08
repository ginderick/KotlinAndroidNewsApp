package com.example.kotlinandroidnewsapp.repository

import androidx.room.Query
import com.example.kotlinandroidnewsapp.api.NewsAPI
import com.example.kotlinandroidnewsapp.db.ArticleDao
import com.example.kotlinandroidnewsapp.model.Article
import javax.inject.Inject

class MainRepository @Inject constructor(
    val articleDao: ArticleDao,
    val newsAPI: NewsAPI
) {

    suspend fun getBreakingNews() = newsAPI.getBreakingNews()

    suspend fun searchNews(searchQuery: String) = newsAPI.searchNews(searchQuery)

    suspend fun insertNewsToDatabase(article: Article) = articleDao.upsert(article)

    suspend fun deleteNewsInDatabase(article: Article) = articleDao.delete(article)

    fun getSavedNews() = articleDao.getAllArticles()


}