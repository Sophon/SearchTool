plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.ksp)
    alias(libs.plugins.micronaut.application)
    alias(libs.plugins.shadow)
    alias(libs.plugins.micronaut.aot)
}

version = "0.1"
group = "io.github.sophon"


repositories {
    mavenCentral()
}

dependencies {
    ksp(libs.micronaut.data.processor)
    ksp(libs.micronaut.http.validation)
    ksp(libs.micronaut.serde.processor)
    implementation(libs.micronaut.http.client)
    implementation(libs.micronaut.websocket)
    implementation(libs.micronaut.data.jdbc)
    implementation(libs.micronaut.flyway)
    implementation(libs.micronaut.kotlin.runtime)
    implementation(libs.micronaut.rabbitmq)
    implementation(libs.micronaut.serde.jackson)
    implementation(libs.micronaut.jdbc.hikari)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib.jdk8)
    runtimeOnly(libs.logback.classic)
    runtimeOnly(libs.flyway.database.postgresql)
    runtimeOnly(libs.postgresql)
    runtimeOnly(libs.jackson.module.kotlin)
    testImplementation(libs.commons.compress)
    testImplementation(libs.testcontainers.core)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.postgresql)
    testRuntimeOnly(libs.junit.platform.launcher)
}



application {
    mainClass = "io.github.sophon.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.toVersion("25")
}




graalvmNative.toolchainDetection = false
graalvmNative {
    binaries {
        all {
            buildArgs.add("-H:+SharedArenaSupport")
        }
    }
}




micronaut {
    version(libs.versions.micronaut.platform.get())
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.github.sophon.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        convertYamlToJava = false
        precomputeOperations = false
        cacheEnvironment = false
        optimizeClassLoading = true
        deduceEnvironment = false
        optimizeNetty = true
        replaceLogbackXml = true
    }

}

tasks.named<io.micronaut.gradle.docker.MicronautDockerfile>("dockerfile") {

    baseImage = "eclipse-temurin:25-jre"
}







// https://docs.gradle.org/current/userguide/upgrading_major_version_9.html#test_task_fails_when_no_tests_are_discovered
tasks.withType<AbstractTestTask>().configureEach {
    failOnNoDiscoveredTests = false
}




