package com.davidbronn.composejokes.data.repository

import com.davidbronn.composejokes.data.data.JokeResponse
import com.davidbronn.composejokes.data.network.safeApiCall
import com.davidbronn.composejokes.domain.api.JokeApi
import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.domain.repository.IJokeRepository
import com.davidbronn.composejokes.utils.Mapper
import com.davidbronn.composejokes.utils.Result
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(
    private val jokeApi: JokeApi,
    private val jokeMapper: Mapper<JokeResponse, Joke>
) : IJokeRepository {

    override suspend fun getJoke(category: String, blackList: String): Result<Joke> {
        return safeApiCall(
            apiCall = { jokeApi.getJoke(category, blackList) },
            mapper = jokeMapper
        )
    }
}