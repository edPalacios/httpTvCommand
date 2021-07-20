package com.gompa.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class BaseDaoTest {

    private val testDispatcher = TestCoroutineDispatcher()
    protected val scope = TestCoroutineScope(testDispatcher)
    protected lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setTransactionExecutor(testDispatcher.asExecutor()) // NOT WORKING https://medium.com/@eyalg/testing-androidx-room-kotlin-coroutines-2d1faa3e674f
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}