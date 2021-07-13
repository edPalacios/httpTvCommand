package com.gompa.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gompa.database.converters.MapConverter
import com.gompa.models.Request

@TypeConverters(MapConverter::class) // TODO pending to add icon property
@Entity
data class RequestEntity(
    @Embedded
    val request: Request,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)