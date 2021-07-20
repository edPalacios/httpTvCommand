package com.gompa.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gompa.database.entities.RequestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {

    companion object {
        const val QUERY_REQUEST = "SELECT * FROM ${RequestEntity.TABLE_NAME}"
    }

    @Query(QUERY_REQUEST)
    fun requests(): Flow<List<RequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RequestEntity)


}