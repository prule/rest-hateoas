plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    alias(libs.plugins.jvm)
    alias(libs.plugins.spring)
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":adapter"))
    implementation(project(":application"))

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