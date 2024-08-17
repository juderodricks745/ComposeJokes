package com.davidbronn.composejokes.data.network

import com.davidbronn.composejokes.utils.Mapper
import com.davidbronn.composejokes.utils.Result
import retrofit2.Response
import timber.log.Timber

suspend fun <T, R> safeApiCall(
    apiCall: suspend () -> Response<T>,
    mapper: Mapper<T, R>
): Result<R> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.Success(data = mapper.map(body))
            } else {
                Result.Error(message = "Received empty response body")
            }
        } else {
            Result.Error(message = response.message())
        }
    } catch (e: Exception) {
        Timber.e(e)
        Result.Error(message = e.message ?: "An unexpected error occurred")
    }
}