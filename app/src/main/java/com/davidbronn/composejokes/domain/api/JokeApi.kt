package com.davidbronn.composejokes.domain.api

import com.davidbronn.composejokes.data.data.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokeApi {

    @GET("{category}")
    suspend fun getJoke(
        @Path("category") category: String,
        @Query("blacklistFlags") blackList: String
    ): Response<JokeResponse>
}