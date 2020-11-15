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
    private val articleDb: ArticleDb
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

    fun getSavedNews(): Flow<PagingData<Article>> {

        return Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            )
        ) {
            articleDb.getArticleDao().getAllArticles()
        }.flow
    }

    suspend fun saveNews(article: Article): Long {
        return articleDb.getArticleDao().upsert(article)
    }

    suspend fun deleteSavedNews(article: Article) {
        return articleDb.getArticleDao().delete(article)
    }


    fun searchNews(searchQuery: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchNewsPagingSource(searchQuery, newsAPI) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}