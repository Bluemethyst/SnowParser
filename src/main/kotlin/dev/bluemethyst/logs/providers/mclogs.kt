package dev.bluemethyst.logs.providers

import dev.bluemethyst.logs.parseLog
import dev.bluemethyst.net.get

fun handleMclogs(text: String): Any {
    val r = get("https://api.mclo.gs/1/raw/$text")
    return parseLog(r.body())
}