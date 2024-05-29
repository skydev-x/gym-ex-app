package com.skydev

import android.app.Application
import com.skydev.gymexercise.di.appModule
import com.skydev.gymexercise.di.repositoryModule
import com.skydev.gymexercise.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GymExApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GymExApplication)
            modules(appModule, repositoryModule, viewModelModule)
        }
    }
}