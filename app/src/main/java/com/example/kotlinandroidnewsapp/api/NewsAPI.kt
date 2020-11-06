package com.example.kotlinandroidnewsapp.api

import com.example.kotlinandroidnewsapp.model.NewsResponse
import com.example.kotlinandroidnewsapp.util.Constants.NEWS_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country")
        countryCode: String = "ph",
        @Query("apiKey")
        apiKey: String = NEWS_API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = NEWS_API_KEY
    ): Response<NewsResponse>

}