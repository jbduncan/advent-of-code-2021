plugins {
  kotlin("jvm") version "1.6.10"
  id("com.diffplug.spotless") version "6.2.2"
  id("com.github.ben-manes.versions") version "0.42.0"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.kotlinStdlib)
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
}

spotless {
  kotlin {
    ktfmt("0.31")
    toggleOffOn()
  }
}
