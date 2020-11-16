package com.example.kotlinandroidnewsapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kotlinandroidnewsapp.api.NewsAPI
import com.example.kotlinandroidnewsapp.db.ArticleDb
import com.example.kotlinandroidnewsapp.model.Article
import com.example.kotlinandroidnewsapp.util.Constants
import retrofit2.HttpException
import java.io.IOException

private const val NEWS_STARTING_PAGE_INDEX = 1
class SearchNewsPagingSource (
    private val query: String,
    private val service: NewsAPI,
    private val articleDb: ArticleDb
): PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX

        return try {
            val response = service.searchNews(query, Constants.NEWS_API_KEY, position)
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

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }
}