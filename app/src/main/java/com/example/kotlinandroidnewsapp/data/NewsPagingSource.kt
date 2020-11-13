package com.example.kotlinandroidnewsapp.data

import android.util.Log
import androidx.paging.PagingSource
import com.example.kotlinandroidnewsapp.api.NewsAPI
import com.example.kotlinandroidnewsapp.model.Article
import com.example.kotlinandroidnewsapp.util.Constants.NEWS_API_KEY
import retrofit2.HttpException
import java.io.IOException


private const val NEWS_STARTING_PAGE_INDEX = 1
class NewsPagingSource (
    private val service: NewsAPI
): PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX

        return try {
            Log.d("url", service.getBreakingNews("us",NEWS_API_KEY, position).toString() )
            Log.d("url", service.toString() )
            val response = service.getBreakingNews("us",NEWS_API_KEY, position)
            Log.d("Response", response.toString())
            Log.d("Response", response.totalResults.toString())
            val repos = response.articles
            LoadResult.Page(
                data = repos,
                prevKey = if (position == NEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        }catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}