// https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html

plugins {
    `kotlin-dsl`
    id("java")
    jacoco
    `jvm-test-suite`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.serialization.gradle.plugin)
    implementation(libs.kapt.gradle.plugin)
}
