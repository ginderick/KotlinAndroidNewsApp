package com.example.kotlinandroidnewsapp.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlinandroidnewsapp.api.NewsAPI

import com.example.kotlinandroidnewsapp.data.NewsRemoteMediator
import com.example.kotlinandroidnewsapp.db.ArticleDb
import com.example.kotlinandroidnewsapp.model.Article
import com.example.kotlinandroidnewsapp.model.NewsResponse
import kotlinx.coroutines.flow.Flow

import retrofit2.Response
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val newsAPI: NewsAPI,
    private val articleDb: ArticleDb
) {

    fun getBreakingNews(): Flow<PagingData<Article>> {

        val pagingSourceFactory = { articleDb.getArticleDao().getAllArticles() }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = NewsRemoteMediator(
                newsAPI,
                articleDb
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


    suspend fun searchNews(searchQuery: String): Response<NewsResponse> {
//        Log.d("Response", newsAPI.searchNews(searchQuery).toString())
        return newsAPI.searchNews(searchQuery)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}