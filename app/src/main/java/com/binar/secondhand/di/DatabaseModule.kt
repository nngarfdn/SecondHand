package com.binar.secondhand.kel2.di

import androidx.room.Room
import com.binar.secondhand.kel2.data.local.room.database.AppDatabase

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(get()
            , AppDatabase::class.java, "mydatabase.db").build()
    }

    //create dao instance here
//    single {
//        get<AppDatabase>().searchHistoryDao()
//    }

//    singleOf(::DbHelper)

}