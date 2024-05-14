package dev.bluemethyst.logs.providers

import dev.bluemethyst.logs.parseLog
import dev.bluemethyst.net.get

fun handleHaste(text: String) {
    val r = get("https://hst.sh/raw/$text")
    parseLog(r.body())
}