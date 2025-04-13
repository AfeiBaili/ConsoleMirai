plugins {
    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.16.0"
}

group = "online.afeibaili"
version = "0.1.0"

dependencies {
    testImplementation(kotlin("test"))
}

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
