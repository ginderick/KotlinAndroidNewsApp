package com.example.kotlinandroidnewsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlinandroidnewsapp.api.NewsAPI


import com.example.kotlinandroidnewsapp.db.ArticleDb
import com.example.kotlinandroidnewsapp.model.Article
import kotlinx.coroutines.flow.Flow

import retrofit2.Response
import javax.inject.Inject

class SavedNewsRepository @Inject constructor(
    private val articleDb: ArticleDb
) {

    fun getSavedNews(): Flow<PagingData<Article>> {

        return Pager(
             PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ) ) {
                articleDb.getArticleDao().getAllArticles()
            }.flow
    }
}


