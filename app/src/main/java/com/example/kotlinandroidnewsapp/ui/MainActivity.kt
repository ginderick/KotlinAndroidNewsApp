package com.example.kotlinandroidnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.kotlinandroidnewsapp.R
import com.example.kotlinandroidnewsapp.api.NewsAPI
import com.example.kotlinandroidnewsapp.db.ArticleDao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var articleDao: ArticleDao

    @Inject
    lateinit var newsAPI: NewsAPI

    private val viewModel: NewsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("articleDao", "ARTICLE_DAO: ${articleDao.hashCode()}")
        Log.d("newsApi", "NEWS_API: ${newsAPI.hashCode()}")

        viewModel.getBreakingNews()

    }


}