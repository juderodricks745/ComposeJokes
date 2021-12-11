package com.davidbronn.composejokes.data.repository

import com.davidbronn.composejokes.data.data.JokeResponse
import com.davidbronn.composejokes.domain.api.JokeApi
import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.domain.repository.IJokeRepository
import com.davidbronn.composejokes.utils.Mapper
import com.davidbronn.composejokes.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(
    private val jokeApi: JokeApi,
    private val jokeMapper: Mapper<JokeResponse, Joke>
) : IJokeRepository {

    override fun getJoke(category: String, blackList: String): Flow<Resource<Joke>> {
        return flow {
            val response = jokeApi.getJoke(category, blackList)
            when (response.isSuccessful) {
                true -> {
                    val body = response.body()
                    if (body != null) {
                        val joke = jokeMapper.map(body)
                        emit(Resource.Success(joke))
                    } else {
                        emit(Resource.Error("Unexpected Error!"))
                    }
                }
                false -> {
                    emit(Resource.Error(response.message()))
                }
            }
        }.catch { e ->
            emit(Resource.Error(e.message ?: "Unexpected Error!"))
        }.flowOn(Dispatchers.IO)
    }
}