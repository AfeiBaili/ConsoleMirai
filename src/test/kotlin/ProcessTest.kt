import kotlinx.coroutines.*
import java.io.BufferedWriter

class Bash {
    private val process: Process = ProcessBuilder("bash").start()
    private val writer: BufferedWriter = process.outputStream.bufferedWriter()
    private val reader = process.inputStream.bufferedReader()

    private val timeOut = 500L

    fun sendCommand(params: String, callback: suspend (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            writer.write("$params\n")
            writer.flush()

            val result = readWithTimeout()
            callback(result)
        }
    }

    private suspend fun readWithTimeout(): String = withContext(Dispatchers.IO) {
        val builder = StringBuilder()
        var lastReadTime = System.currentTimeMillis()

        while (true) {
            val line = withTimeoutOrNull(100) { reader.readLine() }
            if (line != null) {
                builder.appendLine(line)
                lastReadTime = System.currentTimeMillis()
            } else if (System.currentTimeMillis() - lastReadTime > timeOut) {
                break
            }
        }

        return@withContext if (builder.isNotEmpty()) builder.toString() else "操作成功"
    }

    companion object {
        val bash = Bash()

        fun runProcess(vararg params: String): String {
            val process: Process = ProcessBuilder(*params).redirectErrorStream(true).start()
            return process.inputStream.bufferedReader().useLines { lines ->
                lines.joinToString("\n")
            }
        }
    }
}
