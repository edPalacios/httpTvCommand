package com.example.network

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection

data class Request(
    val title: String,
    val url: String,
    val method: HttpMethod = HttpMethod.GET,
    val timeout: Int = 30,
    val retries: Int = 0,
    val followRedir: Boolean,
    val headers: Map<String, String> = emptyMap(),
)

data class Response(val code: Int, val body: String?, val headers: Map<String, String> = emptyMap())

enum class HttpMethod {
    GET,
    POST,
    PATCH,
    PUT,
    OPTIONS,
}

fun executeHttpCall(request: Request): Response {

    val connection: HttpsURLConnection = URL(request.url).openConnection() as HttpsURLConnection

    connection.requestMethod = request.method.toString()
    connection.doOutput = true

    val outputStream: OutputStream = connection.outputStream
    val outputWriter = OutputStreamWriter(outputStream)
    outputWriter.write("")
    outputWriter.flush()

    val body = StringBuffer()

    val inputStream = BufferedReader(InputStreamReader(connection.inputStream)).use {
        var inputLine = it.readLine()
        while (inputLine != null) {
            body.append(inputLine)
            inputLine = it.readLine()
        }
        it.close()
        // TODO: Add main thread callback to parse response
        println(">>>> Response: $body")
    }

    connection.disconnect()

    return Response(connection.responseCode, body.toString())
}
