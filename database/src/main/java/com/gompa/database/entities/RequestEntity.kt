package com.gompa.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.gompa.database.converters.MapConverter
import com.gompa.database.entities.RequestEntity.Companion.TABLE_NAME
import com.gompa.models.Request

@TypeConverters(MapConverter::class) // TODO pending to add icon property
@Entity(
    tableName = TABLE_NAME,
    primaryKeys = ["url"]
)
data class RequestEntity(
    @Embedded
    val request: Request
){
    companion object {
        const val TABLE_NAME = "request"
    }
}