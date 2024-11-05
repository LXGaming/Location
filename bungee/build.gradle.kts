val adventurePlatformVersion: String by project
val bungeecordVersion: String by project
val slf4jVersion: String by project

base {
    archivesName = "location-bungee"
}

dependencies {
    compileJar(project(path = ":location-common")) {
        exclude(module = "brigadier")
        exclude(module = "gson")
        exclude(module = "guava")
        exclude(module = "netty-all")
        exclude(module = "slf4j-api")
    }
    implementation(fileTree("libs") {
        include("*.jar")
    })
    implementation("net.md-5:bungeecord-api:${bungeecordVersion}")
    compileJar("net.kyori:adventure-platform-bungeecord:${adventurePlatformVersion}") {
        exclude(module = "gson")
    }
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
}

artifacts {
    signJar(tasks.shadowJar)
}

tasks.build {
    finalizedBy(tasks.signJar)
}

tasks.compileJava {
    dependsOn(":location-common:build")
}

tasks.jar {
    enabled = false
    dependsOn(tasks.shadowJar)

    manifest {
        attributes(
            "Implementation-Title" to "Location Bungee",
        )
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from (sourceSets.main.get().resources.srcDirs) {
        include("bungee.yml")
        expand("name" to "Location", "version" to project.version.toString())
    }
}

tasks.publish {
    enabled = false
}

tasks.shadowJar {
    archiveClassifier = ""
    configurations = listOf(project.configurations.compileJar.get())

    exclude("META-INF/COPYRIGHT")
    exclude("META-INF/LICENSE")

    relocate("net.kyori.adventure", "io.github.lxgaming.location.lib.adventure")
    relocate("net.kyori.examination", "io.github.lxgaming.location.lib.examination")
    relocate("net.kyori.option", "io.github.lxgaming.location.lib.option")
    relocate("org.checkerframework", "io.github.lxgaming.location.lib.checkerframework")
}

tasks.withType<AbstractPublishToMaven>().forEach {
    it.enabled = false
}

tasks.withType<GenerateMavenPom>().forEach {
    it.enabled = false
}