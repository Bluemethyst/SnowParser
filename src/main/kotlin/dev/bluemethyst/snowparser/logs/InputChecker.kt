package dev.bluemethyst.snowparser.logs

import dev.bluemethyst.snowparser.logs.providers.*

fun checkInputType(input: String): Any {
    val final: Any
    when {
        input.startsWith("https://0x0.st/") -> {
            final = handle0x0(input)
        }
        input.startsWith("https://hst.sh/") -> {
            val remaining = input.removePrefix("https://hst.sh/")
            final = handleHaste(remaining)
        }
        input.startsWith("https://pastebin.com/") -> {
            val remaining = input.removePrefix("https://pastebin.com/")
            final = handlePastebin(remaining)
        }
        input.startsWith("https://pastes.dev/") -> {
            val remaining = input.removePrefix("https://pastes.dev/")
            final = handlePastesDev(remaining)
        }
        input.startsWith("https://mclo.gs/") -> {
            val remaining = input.removePrefix("https://mclo.gs/")
            final = handleMclogs(remaining)
        }
        else -> {
            final = parseLog(input)
        }
    }
    return final
}