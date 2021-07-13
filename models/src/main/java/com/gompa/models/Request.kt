package com.gompa.models

data class Request(
    val title: String = "Request",
    val url: String,
    val method: HttpMethod = HttpMethod.GET,
    val timeout: Long = 30,
    val retry: Boolean = true,
    val followRedir: Boolean = true,
    val headers: Map<String, String> = emptyMap(),
    val body: String? = null,
)