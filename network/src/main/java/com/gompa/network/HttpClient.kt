package com.gompa.network

import com.gompa.models.Request
import com.gompa.models.Response
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit.SECONDS
import okhttp3.Request as OkRequest

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
