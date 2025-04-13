package online.afeibaili

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

object ConsoleMirai : KotlinPlugin(
    JvmPluginDescription(
        id = "online.afeibaili.console",
        name = "ConsoleMirai",
        version = "0.1.0",
    ) {
        author("AfeiBaili")
    }
) {
    override fun onEnable() {
        logger.info("ConsoleMirai加载成功")
        loadListener()
    }
}