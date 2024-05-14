package dev.bluemethyst.logs

data class MinecraftLog(
    val timestamp: String,
    val level: String,
    val errors: List<String>,
    val errorCount: Int,
    val warnings: List<String>,
    val warningCount: Int,
    val problematicClasses: List<String>?
)

// https://github.com/Layers-of-Railways/bot/tree/main/src/logProviders

fun parseLog(log: String): Any {
    if (log.isEmpty()) {
        return "No log data provided."
    }
    val lines = log.split("\n")
    val errors = mutableListOf<String>()
    val warnings = mutableListOf<String>()
    val problematicClasses = mutableListOf<String>()

    // Adjusted regex to match the log format: [12:41:21] [main/INFO]: <TEXTDATA>
    val regex = """\[(.*?)\] \[(.*?)/(.*?)\]: (.*)""".toRegex()

    var thread = ""
    var message = ""

    for (line in lines) {
        val matchResult = regex.find(line)
        if (matchResult != null) {
            val destructured = matchResult.destructured
            thread = destructured.component2()
            val level = destructured.component3()
            message = destructured.component4()

            // Add logic here to identify problematic classes
            // and add them to the problematicClasses list

            when (level) {
                "ERROR" -> errors.add(message)
                "WARN" -> warnings.add(message)
            }
        }
    }

    val errorCount = errors.size
    val warningCount = warnings.size

    return MinecraftLog(thread, message, errors, errorCount, warnings, warningCount, problematicClasses)
}