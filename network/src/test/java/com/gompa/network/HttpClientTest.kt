package com.gompa.network

import com.gompa.models.Request
import org.junit.Test

class HttpClientTest {

    @Test
    fun testExecuteOkHttpCall(){
        val response = executeOkHttp(Request(url="www.google.com"))
        println(response)
    }
}