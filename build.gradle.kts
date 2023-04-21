import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"

    id("org.flywaydb.flyway") version "9.8.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

application {
    mainClass.set("com.example.webapitest.ApplicationKt")
}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(kotlin("stdlib-jdk8"))
    runtimeOnly("mysql:mysql-connector-java:8.0.33")


    // Ktor
    val ktorVersion = "2.2.4"
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")

    val koinVersion = "3.2.0"
    implementation("io.insert-koin:koin-ktor:$koinVersion")

    // exposed
    val exposedVersion = "0.41.1"
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("org.jetbrains.exposed:spring-transaction:$exposedVersion")


    val hikaricpVersion = "5.0.1"
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")

    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.2.4")
    testImplementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-mysql:9.8.3")
        classpath("mysql:mysql-connector-java:8.0.33")
    }
}

flyway {
    url = "jdbc:mysql://localhost:3316/shiharai"
    user = "testuser"
    password = "password"
    locations = arrayOf("filesystem:./database/src/migration")
}