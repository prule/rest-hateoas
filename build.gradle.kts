//https://spring.io/guides/tutorials/spring-boot-kotlin
plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("plugin.allopen") version "1.9.24"
    kotlin("kapt") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jpa")
    annotationProcessor("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
    annotationProcessor("javax.annotation:javax.annotation-api:1.3.2")

    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    implementation("com.google.guava:guava:33.2.1-jre")
    implementation("io.github.xn32:json5k:0.3.0")
//    implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
    implementation(libs.logback.classic)
    implementation(libs.bundles.logging)

    testImplementation(libs.bundles.kotest)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    runtimeOnly("com.h2database:h2")

    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    implementation(kotlin("script-runtime"))

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
