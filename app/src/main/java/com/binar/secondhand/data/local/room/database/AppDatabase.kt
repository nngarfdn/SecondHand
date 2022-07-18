package com.binar.secondhand.kel2.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity

@Database(entities = [SearchHistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {



}