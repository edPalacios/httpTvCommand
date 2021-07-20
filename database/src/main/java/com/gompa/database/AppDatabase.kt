package com.gompa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gompa.database.dao.RequestDao
import com.gompa.database.entities.RequestEntity

@Database(entities = [RequestEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun httpDao(): RequestDao
}


class Database(context: Context) {

    init {
        create(context).apply {
            httpDao = httpDao()
        }
    }

    private fun create(context: Context): AppDatabase {
        val databaseBuilder = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "http-database"
        )
        return databaseBuilder.build()
    }

    companion object {
        lateinit var httpDao: RequestDao
    }
}
