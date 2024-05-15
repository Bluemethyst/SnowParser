package dev.bluemethyst.snowparser.logs

import dev.bluemethyst.snowparser.gson
import dev.bluemethyst.snowparser.pastes.uploadLog

data class MinecraftLog(
    val uploadCode: String?,
    val timestamp: String,
    val mods: Int,
    val warningCount: Int,
    val errorCount: Int,
    val errors: List<String>,
    val warnings: List<String>,
    val potentialProblematicClasses: List<String>?,
    val raw: String?
)

// fix https://chatgpt.com/c/7ac5ca2b-fb9c-4fce-8517-d186e3db0498
// https://github.com/Layers-of-Railways/bot/tree/main/src/logProviders
// https://mclo.gs/SJKlU1M 3 part
// https://mclo.gs/BD2Sqg2 2 part

fun parseLog(log: String, uploadLog: Boolean = true): Any {
    if (log.isEmpty()) {
        return "No log data provided."
    }
    val uploadCode: String? = null
    if (uploadLog) {
        uploadLog(log).toString()
    }
    val lines = log.split("\n")
    val errors = mutableListOf<String>()
    val warnings = mutableListOf<String>()
    val potentialProblematicClasses = mutableListOf<String>()
    val logs: MinecraftLog?
    var timestamp = ""
    var mods = 0
    var errorCount = 0
    var warningCount = 0
    val logPattern = Regex("""\[(.*?)\] \[(.*?)/(.*?)](.*?): (.*?)$""")
    val modPattern = Regex("""Loading (\d+) mods:""")

    for (line in lines) {
        if (line.contains("Found mod file")) {
            mods += 1
        }
        val modMatchResult = modPattern.find(line)
        if (modMatchResult != null) {
            mods += modMatchResult.groupValues[1].toInt()
        }
        val matchResult = logPattern.find(line)
        if (matchResult != null) {
            val (time, thread, level, source, message) = matchResult.destructured
            timestamp = time
            // println("Time: $time, Thread: $thread, Level: $level, Source: $source, Message: $message")
            when (level) {
                "ERROR" -> {
                    errors.add(message)
                    errorCount = errors.size
                }
                "WARN" -> {
                    warnings.add(message)
                    warningCount = warnings.size
                }
            }
        } else {
            potentialProblematicClasses.add(line)
        }
    }
    logs = MinecraftLog(uploadCode, timestamp, mods, warningCount, errorCount, warnings, errors, potentialProblematicClasses, null)
    return if (logs.errors.isEmpty() && logs.warnings.isEmpty() && logs.mods == 0) {
        gson.toJson(mapOf("error" to "Log seems invalid. If you believe this is an error, please create an issue on the GitHub repository."))
    } else {
        gson.toJson(logs)
    }
}