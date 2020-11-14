//package com.example.kotlinandroidnewsapp.data
//
//import android.util.Log
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import androidx.room.withTransaction
//import com.example.kotlinandroidnewsapp.api.NewsAPI
//import com.example.kotlinandroidnewsapp.db.ArticleDb
//import com.example.kotlinandroidnewsapp.db.RemoteKeys
//import com.example.kotlinandroidnewsapp.model.Article
//import com.example.kotlinandroidnewsapp.util.Constants.NEWS_API_KEY
//import retrofit2.HttpException
//import java.io.IOException
//import java.io.InvalidObjectException
//import javax.inject.Inject
//
//private const val NEWS_STARTING_PAGE_INDEX = 1
//
//@OptIn(ExperimentalPagingApi::class)
//class NewsRemoteMediator @Inject constructor(
//    private val service: NewsAPI,
//    private val articleDb: ArticleDb
//) : RemoteMediator<Int, Article>() {
//
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, Article>
//    ): MediatorResult {
//        val page = when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1) ?: NEWS_STARTING_PAGE_INDEX
//
//            }
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                if (remoteKeys == null) {
//                    // The LoadType is PREPEND so some data was loaded before,
//                    // so we should have been able to get remote keys
//                    // If the remoteKeys are null, then we're an invalid state and we have a bug
//                        throw InvalidObjectException ("Remote key and the prevKey should not be null")
//                }
//                // If the previous key is null, then we can't request more data
//                val prevKey = remoteKeys.prevKey
//                if (prevKey == null) {
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                remoteKeys.prevKey
//
//
//            }
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                if (remoteKeys == null || remoteKeys.nextKey == null) {
//                    throw InvalidObjectException("Remote key should not be null for $loadType")
//                }
//                remoteKeys.nextKey
//
//            }
//        }
//
//        try {
//            val response = service.getBreakingNews( apiKey = NEWS_API_KEY, page = page)
//
//            val articles = response.articles
//            val endOfPaginationReached = articles.isEmpty()
//            articleDb.withTransaction {
//
//                //clear all tables in the database
//                if (loadType == LoadType.REFRESH) {
//                    articleDb.getRemoteKeysDao().clearRemoteKeys()
//                    articleDb.getArticleDao().clearArticles()
//                }
//                val prevKey = if (page == NEWS_STARTING_PAGE_INDEX) null else page - 1
//                val nextKey = if (endOfPaginationReached) null else page + 1
//                val keys = articles.map {
//                    RemoteKeys(articleId = it.id, prevKey = prevKey, nextKey = nextKey)
//                }
//                articleDb.getRemoteKeysDao().insertAll(keys)
//                articleDb.getArticleDao().insertAll(articles)
//            }
//            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//        } catch (exception: IOException) {
//            return MediatorResult.Error(exception)
//        } catch (exception: HttpException) {
//            return MediatorResult.Error(exception)
//        }
//
//    }
//
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>) : RemoteKeys? {
//        // Get the last page that was retrieved, that contained items.
//        // From that last page, get the last item
//        return state.pages.lastOrNull() { it.data.isNotEmpty()}?.data?.lastOrNull()
//            ?.let { article ->
//                // Get the remote keys of the last item retrieved
//                articleDb.getRemoteKeysDao().remoteKeysArticlesId(article.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteKeys? {
//        // Get the first page that was retrieved, that contained items.
//        // From that first page, get the first item
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { article ->
//                // Get the remote keys of the first items retrieved
//                Log.d("Response: ", articleDb.getRemoteKeysDao().remoteKeysArticlesId(article.id).toString() )
//                articleDb.getRemoteKeysDao().remoteKeysArticlesId(article.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(
//        state: PagingState<Int, Article>
//    ): RemoteKeys? {
//        // The paging library is trying to load data after the anchor position
//        // Get the item closest to the anchor position
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { article ->
//                articleDb.getRemoteKeysDao().remoteKeysArticlesId(article)
//            }
//        }
//    }
//
//
//
//}