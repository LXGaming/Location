val velocityVersion: String by project

base {
    archivesName = "location-velocity"
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public")
    }
}

dependencies {
    compileJar(project(path = ":location-common")) {
        exclude(module = "adventure-api")
        exclude(module = "adventure-text-serializer-legacy")
        exclude(module = "brigadier")
        exclude(module = "checker-qual")
        exclude(module = "gson")
        exclude(module = "guava")
        exclude(module = "netty-all")
        exclude(module = "slf4j-api")
    }
    implementation(fileTree("libs") {
        include("*.jar")
    })
    annotationProcessor("com.velocitypowered:velocity-api:${velocityVersion}")
    implementation("com.velocitypowered:velocity-api:${velocityVersion}")
}

artifacts {
    signJar(tasks.shadowJar)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
            "Implementation-Title" to "Location Velocity",
        )
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
}

tasks.withType<AbstractPublishToMaven>().forEach {
    it.enabled = false
}

tasks.withType<GenerateMavenPom>().forEach {
    it.enabled = false
}