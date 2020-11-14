package com.example.kotlinandroidnewsapp.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.kotlinandroidnewsapp.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert ( article: Article): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Article>)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): PagingSource<Int, Article>

    @Delete
    suspend fun delete(article: Article)

    @Query("DELETE FROM articles")
    suspend fun clearArticles()
}