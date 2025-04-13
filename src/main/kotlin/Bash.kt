package online.afeibaili

import java.io.IOException
import java.util.*

class Bash {
    var process: Process = ProcessBuilder("bash").start()
    var writer = process.outputStream.bufferedWriter()
    var reader = process.inputStream.bufferedReader()
    var thread = Thread {
        var stringBuilder: StringBuilder = StringBuilder()

        var timer: Timer = Timer()
        val timeOut = 300L
        var lastTime = System.currentTimeMillis()
        var task: TimerTask? = null

        fun appendLine(str: String) {
            stringBuilder.appendLine(str)
            lastTime = System.currentTimeMillis()

            task?.cancel()
            task = object : TimerTask() {
                override fun run() {
                    sendGroup(stringBuilder.toString())
                    stringBuilder.clear()
                }
            }
            timer.schedule(task, timeOut)

        }

        try {
            reader.forEachLine { line -> appendLine(line) }
        } finally {
            timer.cancel()
            reader.close()
            writer.close()
            process.destroy()
        }
    }

    init {
        thread.start()
    }

    fun sendCommand(params: String) {
        try {
            writer.write(params + "\n")
            writer.flush()
        } catch (e: IOException) {
            sendGroup("流已经关闭，正在重置Bash")
            bash = Bash()
        }
    }

    companion object {
        var bash = Bash()

        fun runProcess(vararg params: String): String {
            try {
                if (params.joinToString(" ") == "bash") return "不支持bash，bash操作请用<'>命令"
                var process: Process = ProcessBuilder(*params).redirectErrorStream(true).start()
                var stringBuilder: StringBuilder = StringBuilder()
                process.inputStream.bufferedReader().useLines {
                    stringBuilder.append(it.joinToString("\n"))
                }
                return if (stringBuilder.isNotEmpty()) stringBuilder.toString() else "空"
            } catch (e: IOException) {
                return "不支持的命令：${e.message}"
            }
        }
    }
}