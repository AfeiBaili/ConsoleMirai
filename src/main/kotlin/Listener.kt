package online.afeibaili

import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent

fun loadListener() {
    GlobalEventChannel.filterById<GroupMessageEvent>(2411718391).subscribeAlways<GroupMessageEvent> { event ->
        wrapLogic(event)
    }
    GlobalEventChannel.filterById<FriendMessageEvent>(2411718391L).subscribeAlways<FriendMessageEvent> { event ->
        wrapLogic(event)
    }
}

inline fun <reified BaseEvent : MessageEvent> GlobalEventChannel.filterById(id: Long) = this.filter {
    it is BaseEvent && it.sender.id == id
}

suspend fun wrapLogic(event: MessageEvent) {
    subject = event.subject

    var parsing: String? = parsing(event.message.contentToString())
    parsing?.let { subject!!.sendMessage(it) }
}