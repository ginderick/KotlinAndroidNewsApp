package com.example.kotlinandroidnewsapp.di

import android.app.Application
import com.example.kotlinandroidnewsapp.api.NewsAPI
import com.example.kotlinandroidnewsapp.util.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun provideNewsAPI(retrofit: Retrofit.Builder): NewsAPI {
        return retrofit
            .build()
            .create(NewsAPI::class.java)
    }

    /* @Singleton
 @Provides
 fun provideNewsApiService(newsApi: NewsApi): NewsApiService {
     return NewsApiService(newsApi)
 }*/


}