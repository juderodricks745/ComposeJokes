package com.davidbronn.composejokes.di

import com.davidbronn.composejokes.data.data.JokeResponse
import com.davidbronn.composejokes.data.mapper.JokeMapper
import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.utils.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MapperModule {
    @Binds
    abstract fun bindJokeMapper(mapper: JokeMapper): Mapper<JokeResponse, Joke>
}