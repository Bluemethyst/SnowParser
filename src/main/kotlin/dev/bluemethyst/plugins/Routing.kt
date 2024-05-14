package dev.bluemethyst.plugins

import dev.bluemethyst.logs.parseLog
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/log") {
            val text = call.receiveText()
            val parsed = parseLog(text)
            call.respondText(parsed.toString())
        }
        staticResources("/", "static") {
            default("/favicon.ico")
        }

    }
}
