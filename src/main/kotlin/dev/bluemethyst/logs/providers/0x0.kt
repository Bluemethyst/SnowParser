package dev.bluemethyst.logs.providers

import dev.bluemethyst.logs.parseLog
import dev.bluemethyst.net.get

fun handle0x0(text: String): Any {
    val r = get(text)
    return parseLog(r.body())
}