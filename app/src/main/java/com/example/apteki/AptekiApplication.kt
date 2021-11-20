package com.example.apteki

import android.app.Application
import com.example.apteki.di.appModule
import com.example.apteki.di.networkModule
import com.example.apteki.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AptekiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(listOf(appModule, networkModule, viewModelsModule))
        }
    }
}