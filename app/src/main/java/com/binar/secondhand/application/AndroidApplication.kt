package com.binar.secondhand.application

import android.app.Application
import com.binar.secondhand.di.repositoryModule
import com.binar.secondhand.di.viewModelModule
import com.binar.secondhand.di.databaseModule
import com.binar.secondhand.di.datastoreModule
import com.binar.secondhand.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApplication: Application() {

    override fun onCreate() {
        super.onCreate()
//        val preferences =
//            this.getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)
//        val token = preferences.getString(LoginFragment.TOKEN, "")
        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(networkModule, databaseModule, datastoreModule, repositoryModule, viewModelModule)
//            if (!token.isNullOrEmpty()){
//                koin.setProperty("access_token", token)
//            }
        }


    }


}