plugins {
    id("net.kyori.blossom")
}

val adventureVersion: String by project
val checkerqualVersion: String by project
val guavaVersion: String by project
val rxjavaVersion: String by project

base {
    archivesName = "location-api"
}

dependencies {
    api("com.google.guava:guava:${guavaVersion}")
    api("io.reactivex.rxjava3:rxjava:${rxjavaVersion}")
    api("net.kyori:adventure-api:${adventureVersion}")
    api("org.checkerframework:checker-qual:${checkerqualVersion}")
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        named<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
            }
        }
    }
}

tasks.processResources {
    from("../LICENSE")
    rename("LICENSE", "LICENSE-Location")
}

tasks.shadowJar {
    enabled = false
}