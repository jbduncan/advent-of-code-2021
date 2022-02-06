plugins {
    kotlin("jvm") version "1.6.10"
    id("com.diffplug.spotless") version "6.2.0"
    id("com.github.ben-manes.versions") version "0.41.0"
}

group = "me.jbduncan.adventofcode2021.day1"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinStdlib)

    testImplementation(libs.kotestRunnerJunit5)
    testImplementation(libs.kotestAssertions)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            javaParameters = true
        }
    }

    test {
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = archiveVersion
            attributes["Main-Class"] = "${project.group}.AppKt"
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }

    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}

spotless {
    kotlin {
        ktfmt("0.30")
    }
}
