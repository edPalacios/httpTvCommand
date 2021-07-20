package com.gompa.database.converters

import androidx.room.TypeConverter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

object MapConverter {

    private val mapper by lazy { ObjectMapper() }

    @TypeConverter
    fun toMap(value: String): Map<String, String> {
        val type = object : TypeReference<Map<String, String>>() {}
        return mapper.readValue(value, type)
    }

    @TypeConverter
    fun toString(map: Map<String, String>): String = mapper.writeValueAsString(map)
}