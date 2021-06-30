package com.example.network

import okhttp3.*
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaType
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection

import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.http.RetryAndFollowUpInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Request as OkRequest
import java.util.concurrent.TimeUnit.SECONDS


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

data class Response(val code: Int, val body: String?, val headers: Map<String, String> = emptyMap())

enum class HttpMethod {
    GET,
    POST,
    PATCH,
    PUT,
    OPTIONS,
}

fun executeOkHttp(request: Request): Response {
    val client = OkHttpClient.Builder()
        .readTimeout(request.timeout, SECONDS)
        .writeTimeout(request.timeout, SECONDS)
        .connectTimeout(request.timeout, SECONDS)
        .followRedirects(request.followRedir)
        .followSslRedirects(request.followRedir)
        .addInterceptor(HttpLoggingInterceptor())
        .retryOnConnectionFailure(request.retry)
        .build()

    val rq = OkRequest.Builder()
        .method(
            method = request.method.toString(),
            body = request.body?.toRequestBody(request.headers["Content-Type"]?.toMediaType())
        )
        .url(request.url.prependProtocol())
        .headers(request.headers.toHeaders())
        .build()

    val response = client.newCall(rq).execute()

    return Response(
        body = response.body?.string(),
        code = response.code,
        headers = response.headers.toMap()
    )
}

private fun String.prependProtocol(): String = if (startsWith("http")) this else "https://" + this
