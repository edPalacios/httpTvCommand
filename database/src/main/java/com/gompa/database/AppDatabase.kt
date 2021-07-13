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
        db = create(context)
    }

    private fun create(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "http-database"
    ).build()

    companion object {
        lateinit var db : AppDatabase
    }
}
