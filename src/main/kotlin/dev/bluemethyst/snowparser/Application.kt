package dev.bluemethyst.snowparser

import com.google.gson.Gson
import dev.bluemethyst.snowparser.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

val gson: Gson = Gson()

fun main() {
    embeddedServer(
        Netty,
        port = 9976,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureRouting()
}