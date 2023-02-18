val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
    // KSP
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
    //Dokka
    id("org.jetbrains.dokka") version "1.7.20"
}

group = "resa.mario"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor Core
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")

    // Motor de ktor
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")

    // Json content negotiation
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // JWT
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")

    // Logging
    //implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    // Test
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Koin
    implementation("io.insert-koin:koin-ktor:3.3.0") // Koin para Ktor
    implementation("io.insert-koin:koin-logger-slf4j:3.3.0") // Koin para Ktor con Logger
    implementation("io.insert-koin:koin-annotations:1.1.0") // Si usamos Koin con KSP Anotaciones
    ksp("io.insert-koin:koin-ksp-compiler:1.1.0") // Si usamos Koin con KSP Anotaciones

    // Cache
    implementation("io.github.reactivecircus.cache4k:cache4k:0.9.0")

    // BCrypt
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")

    // Dokka
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")
}

// KSP II -> Para Annotations
sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

// Docker, cuidado con las mayusculas en el nombre de la imagen y de los archivos en la raiz
ktor {
    docker {
        localImageName.set("empleado-departamento-ktor-opcional")
        imageTag.set("0.0.1-preview")
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        portMappings.set(
            listOf(
                io.ktor.plugin.features.DockerPortMapping(
                    6969,
                    6969,
                    io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                )
            )
        )
    }
}

// Java 17
// https://kotlinlang.org/docs/get-started-with-jvm-gradle-project.html#explore-the-build-script
kotlin { // Extension for easy setup
    jvmToolchain(17) // Target version of generated JVM bytecode
}