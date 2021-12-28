package com.digitalcity.apteki

import android.app.Application
import com.digitalcity.apteki.di.appModule
import com.digitalcity.apteki.di.networkModule
import com.digitalcity.apteki.di.viewModelsModule
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