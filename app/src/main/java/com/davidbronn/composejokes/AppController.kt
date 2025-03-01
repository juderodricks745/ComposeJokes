package com.davidbronn.composejokes

import android.app.Application
import com.davidbronn.composejokes.di.jokeModule
import com.davidbronn.composejokes.utils.DebugTimberTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        // Debug only Timber Tree
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTimberTree())
        }

        // Initialize Koin
        startKoin {
            androidContext(this@AppController)
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            modules(jokeModule)
        }
    }
}