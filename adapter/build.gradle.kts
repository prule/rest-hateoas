
//https://spring.io/guides/tutorials/spring-boot-kotlin
plugins {
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    alias(libs.plugins.jvm)
    alias(libs.plugins.spring)
    alias(libs.plugins.jpa)
    alias(libs.plugins.allopen)
    alias(libs.plugins.kapt)
    alias(libs.plugins.serialization)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

group = "com.example.rest_hateoas.adapter"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {

    implementation(project(":port"))
    implementation(project(":application"))

    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jakarta")
    annotationProcessor("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
    annotationProcessor("javax.annotation:javax.annotation-api:1.3.2")

    api("org.springframework.boot:spring-boot-starter-hateoas")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    implementation("com.google.guava:guava:33.3.0-jre")
    implementation("io.github.xn32:json5k:0.3.0")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
    implementation(libs.logback.classic)
    implementation(libs.bundles.logging)

    testImplementation(libs.bundles.kotest)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    implementation(kotlin("script-runtime"))

    // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    testImplementation("io.rest-assured:rest-assured:5.5.0")
    // https://mvnrepository.com/artifact/org.testcontainers/postgresql
    testImplementation("org.testcontainers:postgresql:1.20.1")
    // https://mvnrepository.com/artifact/org.testcontainers/junit-jupiter
    testImplementation("org.testcontainers:junit-jupiter:1.20.1")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.4")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}