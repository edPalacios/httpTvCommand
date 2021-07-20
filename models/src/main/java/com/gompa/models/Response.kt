package com.gompa.models

data class Response(
    val code: Int,
    val body: String?,
    val headers: Map<String, String> = emptyMap()
)