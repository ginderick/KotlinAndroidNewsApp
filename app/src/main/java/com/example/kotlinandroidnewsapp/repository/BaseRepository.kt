package com.example.kotlinandroidnewsapp.repository



import com.example.kotlinandroidnewsapp.util.Output
import retrofit2.Response
import java.io.IOException

open class BaseRepository {
    suspend fun <T: Any> safeApiCall(call: suspend () -> Response<T>, error:String): T? {
        val result = newsApiOutput(call, error)
        var output: T? = null
        when(result) {
            is Output.Success -> output = result.output
        }
        return output
    }

    private suspend fun <T: Any> newsApiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): Output<T> {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return@newsApiOutput Output.Success(resultResponse)
            }
        }
        return Output.Error(IOException("Something went wrong: $error"))
    }
}