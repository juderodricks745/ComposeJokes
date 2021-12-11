package com.davidbronn.composejokes.domain.repository

import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IJokeRepository {
    fun getJoke(category: String, blackList: String): Flow<Resource<Joke>>
}