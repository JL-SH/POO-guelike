plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "1.9.0"
}

group = "es.prog2425.u4u5u6libre"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.lalyos:jfiglet:0.0.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDirs("src/main/controllers", "src/main/models", "src/main/views", "src/main/main")
        }
    }
}

tasks.withType<JavaExec> {
    standardOutput = System.out
}