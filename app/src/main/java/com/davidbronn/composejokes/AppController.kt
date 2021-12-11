package com.davidbronn.composejokes

import android.app.Application
import com.davidbronn.composejokes.utils.DebugTimberTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        // Debug only Timber Tree
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTimberTree())
        }
    }
}