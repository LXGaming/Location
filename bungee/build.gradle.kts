import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers

val adventureVersion: String by project
val adventurePlatformVersion: String by project
val bungeecordVersion: String by project
val rxjavaVersion: String by project
val slf4jVersion: String by project

base {
    archivesName = "location-bungee"
}

dependencies {
    compileJar(project(path = ":location-api")) {
        isTransitive = false
    }
    compileJar(project(path = ":location-common")) {
        isTransitive = false
    }
    implementation(fileTree("libs") {
        include("BungeeCord.jar")
    })
    implementation("net.md-5:bungeecord-api:${bungeecordVersion}")
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")

    // Libraries
    compileJar("net.kyori:adventure-api:${adventureVersion}")
    compileJar("net.kyori:adventure-platform-bungeecord:${adventurePlatformVersion}") {
        exclude(module = "gson")
    }
    compileJar("net.kyori:adventure-text-serializer-legacy:${adventureVersion}")
    compileJar("io.reactivex.rxjava3:rxjava:${rxjavaVersion}")
}

artifacts {
    signJar(tasks.shadowJar)
}

tasks.build {
    finalizedBy(tasks.signJar)
}

tasks.compileJava {
    dependsOn(":location-api:build", ":location-common:build", downloadLibraries)
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
}

tasks.withType<AbstractPublishToMaven>().forEach {
    it.enabled = false
}

tasks.withType<GenerateMavenPom>().forEach {
    it.enabled = false
}

val downloadLibraries = tasks.register<Download>("downloadLibraries") {
    src("https://ci.md-5.net/job/BungeeCord/1881/artifact/bootstrap/target/BungeeCord.jar")
    dest("libs/BungeeCord.jar")
    onlyIfModified(true)
}

rootProject.idea.project.settings.taskTriggers.afterSync(downloadLibraries)
project.eclipse.synchronizationTasks(downloadLibraries)