plugins {
    id("shared")
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.7"
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
