package com.binar.secondhand

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.binar.secondhand.di.repositoryModule
import com.binar.secondhand.di.viewModelModule
import com.binar.secondhand.di.databaseModule
import com.binar.secondhand.di.datastoreModule
import com.binar.secondhand.di.networkModule
import com.google.firebase.FirebaseApp
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

        fun getSharedPreferences(): SharedPreferences = instance.getSharedPreferences(
            "SECONDHAND_PREF",
            Context.MODE_PRIVATE
        )
    }
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@SecondHandApp)
            modules(
                listOf(
                    networkModule, databaseModule, datastoreModule, repositoryModule, viewModelModule
                )
            )
        }
    }
}