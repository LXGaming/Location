val adventureVersion: String by project
val brigadierVersion: String by project
val gsonVersion: String by project
val junitVersion: String by project
val nettyVersion: String by project
val slf4jVersion: String by project

base {
    archivesName = "location-common"
}

dependencies {
    api(project(path = ":location-api"))
    api("com.google.code.gson:gson:${gsonVersion}")
    api("com.mojang:brigadier:${brigadierVersion}")
    api("io.netty:netty-all:${nettyVersion}")
    api("net.kyori:adventure-text-serializer-legacy:${adventureVersion}")
    api("org.slf4j:slf4j-api:${slf4jVersion}")
}

tasks.compileJava {
    dependsOn(":location-api:build")
}

tasks.publish {
    enabled = false
}

tasks.withType<AbstractPublishToMaven>().forEach {
    it.enabled = false
}

tasks.withType<GenerateMavenPom>().forEach {
    it.enabled = false
}