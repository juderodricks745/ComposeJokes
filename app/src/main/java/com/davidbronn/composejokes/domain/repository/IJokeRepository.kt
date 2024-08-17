package com.davidbronn.composejokes.domain.repository

import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.utils.Result

interface IJokeRepository {
    suspend fun getJoke(category: String, blackList: String): Result<Joke>
}