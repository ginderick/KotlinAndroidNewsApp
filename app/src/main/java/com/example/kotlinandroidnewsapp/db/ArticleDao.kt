package com.example.kotlinandroidnewsapp.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.kotlinandroidnewsapp.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (article: Article): Long

    @Query("UPDATE articles SET isSaved = 1 WHERE url = :url ")
    suspend fun update(url: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Article>)

    @Query("SELECT * FROM articles WHERE isSaved = 0 ORDER BY id ASC")
    fun getAllArticles(): PagingSource<Int, Article>

    @Query("SELECT * FROM articles WHERE isSaved = 1 ORDER BY id ASC")
    fun getAllSavedArticles(): PagingSource<Int, Article>

    @Delete
    suspend fun delete(article: Article)

    @Query("DELETE FROM articles WHERE isSaved = 0")
    suspend fun clearArticles()
}