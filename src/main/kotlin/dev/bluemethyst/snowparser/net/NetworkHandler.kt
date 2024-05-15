package dev.bluemethyst.snowparser.net

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

val client: HttpClient = HttpClient.newBuilder().build()

fun get(url: String): HttpResponse<String> {
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    return response
}

fun post(url: String, body: String): HttpResponse<String>? {
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    return response
}
