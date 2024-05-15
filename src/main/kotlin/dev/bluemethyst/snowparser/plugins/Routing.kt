package dev.bluemethyst.snowparser.plugins

import dev.bluemethyst.snowparser.gson
import dev.bluemethyst.snowparser.logs.checkInputType
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
            val parsed = checkInputType(text)
            call.respondText(parsed.toString())
        }
        get("/log/{code}") {
            val code = call.parameters["code"]
            if (code != null) {
                // handle code shit
            } else {
                call.respondText(gson.toJson(mapOf("error" to "No code provided, usage is /log/{code}")))
            }
        }
        staticResources("/", "static") {
            default("/favicon.ico")
        }
    }
}
