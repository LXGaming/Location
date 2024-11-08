import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    id("maven-publish")
    id("signing")
    id("com.gradleup.shadow") version "8.3.5" apply false
    id("de.undercouch.download") version "5.6.0" apply false
    id("net.kyori.blossom") version "2.1.0" apply false
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.9"
}

subprojects {
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "de.undercouch.download")
    apply(plugin = "eclipse")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.gradle.plugin.idea-ext")
    apply(plugin = "signing")

    val annotationsVersion: String by project
    val junitVersion: String by project

    group = "io.github.lxgaming"

    val compileJar: Configuration by configurations.creating
    val signJar: Configuration by configurations.creating

    configurations {
        implementation {
            extendsFrom(compileJar)
        }
    }

    repositories {
        mavenCentral()
        maven {
            name = "minecraft"
            url = uri("https://libraries.minecraft.net")
        }
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }

    dependencies {
        implementation("org.jetbrains:annotations:${annotationsVersion}")
        testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.base.archivesName.get()
                version = project.version.toString()
                pom {
                    name = "Location"
                    description = "Player location tracking for Waterfall and Velocity"
                    url = "https://github.com/LXGaming/Location"
                    developers {
                        developer {
                            id = "lxgaming"
                            name = "LXGaming"
                        }
                    }
                    issueManagement {
                        system = "GitHub Issues"
                        url = "https://github.com/LXGaming/Location/issues"
                    }
                    licenses {
                        license {
                            name = "The Apache License, Version 2.0"
                            url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                        }
                    }
                    scm {
                        connection = "scm:git:https://github.com/LXGaming/Location.git"
                        developerConnection = "scm:git:https://github.com/LXGaming/Location.git"
                        url = "https://github.com/LXGaming/Location"
                    }
                }
            }
        }
        repositories {
            if (project.hasProperty("sonatypeUsername") && project.hasProperty("sonatypePassword")) {
                maven {
                    name = "sonatype"
                    url = if (project.version.toString().contains("-SNAPSHOT")) {
                        uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
                    } else {
                        uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                    }

                    credentials {
                        username = project.property("sonatypeUsername").toString()
                        password = project.property("sonatypePassword").toString()
                    }
                }
            }
        }
    }

    signing {
        if (project.hasProperty("signingKey") && project.hasProperty("signingPassword")) {
            useInMemoryPgpKeys(
                project.property("signingKey").toString(),
                project.property("signingPassword").toString()
            )
        }

        sign(publishing.publications["maven"])
    }

    tasks.jar {
        manifest {
            attributes(
                "Implementation-Title" to "Location",
                "Implementation-Vendor" to "LX_Gaming",
                "Implementation-Version" to project.version.toString(),
                "Specification-Title" to "Location",
                "Specification-Vendor" to "LX_Gaming",
                "Specification-Version" to "1"
            )
        }
    }

    tasks.javadoc {
        isFailOnError = false
        options {
            this as CoreJavadocOptions

            addStringOption("Xdoclint:none", "-quiet")
        }
    }

    tasks.test {
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }

        useJUnitPlatform()
    }

    tasks.register("signJar") {
        doFirst {
            if (!project.hasProperty("signing.keyStorePath") || !project.hasProperty("signing.secretKeyRingFile")) {
                project.logger.warn("========== [WARNING] ==========")
                project.logger.warn("")
                project.logger.warn("   This build is not signed!   ")
                project.logger.warn("")
                project.logger.warn("========== [WARNING] ==========")
                throw StopExecutionException()
            }
        }

        doLast {
            signJar.allArtifacts.files.forEach {
                ant.withGroovyBuilder {
                    "signjar"(
                        "jar" to it,
                        "alias" to project.property("signing.alias"),
                        "storepass" to project.property("signing.keyStorePassword"),
                        "keystore" to project.property("signing.keyStorePath"),
                        "preservelastmodified" to project.property("signing.preserveLastModified"),
                        "tsaurl" to project.property("signing.timestampAuthority"),
                        "digestalg" to project.property("signing.digestAlgorithm"))
                }
                project.logger.lifecycle("JAR Signed: ${it.name}")

                signing.sign(it)
                project.logger.lifecycle("PGP Signed: ${it.name}")
            }
        }
    }
}

tasks.jar {
    enabled = false
}