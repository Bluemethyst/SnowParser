package dev.bluemethyst.logs.providers

import dev.bluemethyst.logs.parseLog
import dev.bluemethyst.net.get

fun handlePastebin(text: String): Any {
    val r = get("https://pastebin.com/raw/$text")
    return parseLog(r.body())
}