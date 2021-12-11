package com.davidbronn.composejokes.di

import com.davidbronn.composejokes.domain.api.JokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    fun provideJokeApiService(retrofit: Retrofit): JokeApi = retrofit.create(JokeApi::class.java)
}