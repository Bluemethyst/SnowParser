package dev.bluemethyst.snowparser.pastes

import dev.bluemethyst.snowparser.gson
import dev.bluemethyst.snowparser.logs.parseLog
import dev.bluemethyst.snowparser.net.*
import io.ktor.http.*

data class ResponseData(val key: String)

fun getLog(code: String): Any? {
    val response = get("https://api.pastes.dev/$code")
    return if (response.statusCode() != 200) {
        gson.toJson(mapOf("error" to "Invalid code provided, log may not exist. Usage is /log/{code}"))
    } else {
        parseLog(response.body(), false)
    }
}

fun uploadLog(log: String): Any {
    val headers = mapOf(
        HttpHeaders.ContentType to ContentType.Text.Plain.toString(),
        HttpHeaders.UserAgent to "SnowParser"
    )
    val response = post("https://api.pastes.dev/post", log, headers)
    if (response?.statusCode() == 200 || response?.statusCode() == 201) {
        println(response.body())
        val data = gson.fromJson(response.body(), ResponseData::class.java)
        return data.key
    } else {
        return "Failed to upload log."
    }
}