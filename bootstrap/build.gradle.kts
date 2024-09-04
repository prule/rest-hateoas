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

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":adapter"))
    implementation(project(":domain"))
    implementation(project(":port"))

    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    implementation("com.google.guava:guava:33.3.0-jre")
    implementation("io.github.xn32:json5k:0.3.0")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
    implementation(libs.logback.classic)
    implementation(libs.bundles.logging)

    testImplementation(libs.bundles.kotest)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}