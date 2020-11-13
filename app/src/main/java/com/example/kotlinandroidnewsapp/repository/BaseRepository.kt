//package com.example.kotlinandroidnewsapp.repository
//
//
//
//import com.example.kotlinandroidnewsapp.util.Resource
//import retrofit2.Response
//import java.io.IOException
//
//open class BaseRepository {
//    suspend fun <T: Any> safeApiCall(call: suspend () -> Response<T>, error:String): T? {
//        val result = newsApiOutput(call, error)
//        var output: T? = null
//        when(result) {
//            is Resource.Success -> output = result.output
//        }
//        return output
//    }
//
//    private suspend fun <T: Any> newsApiOutput(
//        call: suspend () -> Response<T>,
//        error: String
//    ): Resource<T> {
//        val response = call.invoke()
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                return@newsApiOutput Resource.Success(resultResponse)
//            }
//        }
//        return Resource.Error(IOException("Something went wrong: $error"))
//    }
//}