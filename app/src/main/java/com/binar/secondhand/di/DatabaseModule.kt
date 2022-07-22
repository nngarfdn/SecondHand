package com.binar.secondhand.di

import androidx.room.Room
import com.binar.secondhand.data.local.room.database.AppDatabase
import com.binar.secondhand.data.local.room.service.DbHelper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(get()
            , AppDatabase::class.java, "mydatabase.db").allowMainThreadQueries().build()
    }


    //create dao instance here
    single {
        get<AppDatabase>().searchHistoryDao()
    }
    single {
        get<AppDatabase>().productDao()
    }
    single {
        get<AppDatabase>().remoteKeysDao()
    }

    singleOf(::DbHelper)
}


