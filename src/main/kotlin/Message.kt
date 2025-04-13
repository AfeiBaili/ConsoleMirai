package online.afeibaili

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.mamoe.mirai.contact.Contact

var subject: Contact? = null


fun parsing(message: String): String? {
    if (message.startsWith("\"")) {
        var strings = message.substring(1).trim().split("\\s".toRegex()).toTypedArray()
        if (strings.isNotEmpty()) return Bash.runProcess(*strings)
    }
    if (message.startsWith("\'")) {
        var string = message.substring(1).trim()
        Bash.bash.sendCommand(string)
    }
    return null
}

fun sendGroup(message: String) {
    CoroutineScope(Dispatchers.Default).launch {
        subject?.sendMessage(message)
    }
}