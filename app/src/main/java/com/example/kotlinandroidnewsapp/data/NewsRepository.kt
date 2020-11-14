package com.example.kotlinandroidnewsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlinandroidnewsapp.api.NewsAPI


import com.example.kotlinandroidnewsapp.db.ArticleDb
import com.example.kotlinandroidnewsapp.model.Article
import com.example.kotlinandroidnewsapp.model.NewsResponse
import kotlinx.coroutines.flow.Flow

import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsAPI: NewsAPI,
) {

    fun getBreakingNews(): Flow<PagingData<Article>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(newsAPI) }
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