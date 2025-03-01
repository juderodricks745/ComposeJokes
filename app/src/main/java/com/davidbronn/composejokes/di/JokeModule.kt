package com.davidbronn.composejokes.di

import com.davidbronn.composejokes.BuildConfig
import com.davidbronn.composejokes.data.data.JokeResponse
import com.davidbronn.composejokes.data.mapper.JokeMapper
import com.davidbronn.composejokes.data.repository.JokeRepositoryImpl
import com.davidbronn.composejokes.domain.api.JokeApi
import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.domain.repository.IJokeRepository
import com.davidbronn.composejokes.presentation.JokeViewModel
import com.davidbronn.composejokes.utils.Mapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val jokeModule = module {

    single<Mapper<JokeResponse, Joke>> { JokeMapper() }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(JokeApi::class.java)
    }

    single<IJokeRepository> { JokeRepositoryImpl(get(), get()) }
    viewModel { JokeViewModel(get()) }
}