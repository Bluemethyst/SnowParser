package dev.bluemethyst.logs

// Define a data class for Minecraft logs
data class MinecraftLog(
    val timestamp: String, // Timestamp of the log
    val errors: List<String>, // List of error messages
    val errorCount: Int, // Count of error messages
    val warnings: List<String>, // List of warning messages
    val warningCount: Int, // Count of warning messages
    val problematicClasses: List<String>? // List of problematic classes (optional)
)


// fix https://chatgpt.com/c/7ac5ca2b-fb9c-4fce-8517-d186e3db0498


fun parseLog(log: String): Any {
    if (log.isEmpty()) {
        return "No log data provided."
    }
    val lines = log.split("\n")
    val errors = mutableListOf<String>()
    val warnings = mutableListOf<String>()
    val problematicClasses = mutableListOf<String>()
    var logs: MinecraftLog? = null

    val logPattern = Regex("""\[(.*?)\] \[(.*?)/(.*?)](.*?): (.*?)$""")

    for (line in lines) {
        val matchResult = logPattern.find(line)
        if (matchResult != null) {
            val (timestamp, thread, level, source, message) = matchResult.destructured

            when (level) {
                "ERROR" -> errors.add(message)
                "WARN" -> warnings.add(message)
            }

            val errorCount = errors.size
            val warningCount = warnings.size

            logs = MinecraftLog(timestamp, errors, errorCount, warnings, warningCount, problematicClasses)
        }
    }
    return logs ?: "No logs were parsed, internal error?"
}