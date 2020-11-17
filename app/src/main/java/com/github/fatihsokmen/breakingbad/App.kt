package com.github.fatihsokmen.breakingbad

import android.app.Application
import com.github.fatihsokmen.breakingbad.core.module.appModule
import com.github.fatihsokmen.breakingbad.home.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule, homeModule
                )
            )
        }
    }
}