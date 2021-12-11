package com.davidbronn.composejokes.di

import com.davidbronn.composejokes.data.repository.JokeRepositoryImpl
import com.davidbronn.composejokes.domain.repository.IJokeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideJokeRepository(repository: JokeRepositoryImpl): IJokeRepository
}