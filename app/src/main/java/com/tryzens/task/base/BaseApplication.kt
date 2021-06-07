package com.tryzens.task.base

import android.app.Application
import com.tryzens.task.di.apiModule
import com.tryzens.task.di.networkModule
import com.tryzens.task.di.repoModule
import com.tryzens.task.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BaseApplication)
            modules(
                networkModule,
                viewModelModule,
                repoModule,
                apiModule
            )
        }
    }
}