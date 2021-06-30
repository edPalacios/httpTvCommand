package com.gompa.network

import com.gompa.network.Request
import com.gompa.network.executeOkHttp
import org.junit.Test

class HttpClientTest {

    @Test
    fun testExecuteOkHttpCall(){
        val response = executeOkHttp(Request(url="www.google.com"))
        println(response)
    }
}