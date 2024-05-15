package dev.bluemethyst.snowparser.logs.providers

import dev.bluemethyst.snowparser.gson
import dev.bluemethyst.snowparser.logs.parseLog
import dev.bluemethyst.snowparser.net.get

fun handleHaste(text: String): Any {
    try {
        val r = get("https://hst.sh/raw/$text")
        return parseLog(r.body())
    } catch (e: Exception) {
        return gson.toJson(mapOf("error" to "Failed to fetch log, make sure the paste exists and is public or that you have inputted valid data."))
    }
}