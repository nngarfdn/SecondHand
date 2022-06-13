package com.binar.secondhand

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SecondHandApp: Application() {


    init {
        instance = this
    }
    companion object {
        private lateinit var instance: SecondHandApp

        fun getContext(): Context {
            return instance.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@SecondHandApp)
            modules(
                listOf(
//                    preferencesModule,
//                    databaseModule,
//                    networkModule,
//                    repositoryModule,
//                    viewModelModule
                )
            )
        }
    }
}