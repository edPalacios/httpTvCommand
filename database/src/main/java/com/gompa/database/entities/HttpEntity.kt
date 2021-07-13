package com.gompa.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HttpEntity(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String,
//    val method: HttpMethod = HttpMethod.GET,
    @ColumnInfo(name = "timeout")
    val timeout: Long = 30,
    @ColumnInfo(name = "retry")
    val retry: Boolean = true,
    @ColumnInfo(name = "followRedirection")
    val followRedirection: Boolean = true,
//    @ColumnInfo(name = "headers")
//    val headers: Map<String, String> = emptyMap(),
    @ColumnInfo(name = "body")
    val body: String? = null,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)