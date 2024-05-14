package dev.bluemethyst.logs.providers

import dev.bluemethyst.logs.parseLog
import dev.bluemethyst.net.get

fun handlePastesDev(text: String): Any {
    val r = get("https://api.pastes.dev/$text")
    return parseLog(r.body())
}