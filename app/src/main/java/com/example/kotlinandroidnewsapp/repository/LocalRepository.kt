//package com.example.kotlinandroidnewsapp.repository
//
//import com.example.kotlinandroidnewsapp.db.ArticleDao
//import com.example.kotlinandroidnewsapp.db.ArticleDb
//import com.example.kotlinandroidnewsapp.model.Article
//import javax.inject.Inject
//
//class LocalRepository @Inject constructor(
//    val db: ArticleDao
//) : BaseRepository() {
//
//    suspend fun insertNewsToDb(article: Article) = db.upsert(article)
//
//    suspend fun deleteNews(article: Article) = db.delete(article)
//
//    fun getSavedNews() = db.getAllArticles()
//}