
plugins {
    id("buildlogic.kotlin-library-conventions")
}

group = "com.example.rest_hateoas.application"
version = "0.0.1-SNAPSHOT"

dependencies {


    implementation(libs.logback.classic)
    implementation(libs.bundles.logging)
    /*
    So that we can use value classes in our domain model and REST models, we need to add serialization:
        kotlinx-serialization-core
    Note that in adapters, we also need jackson-module-kotlin so spring boot serialises them properly.
     */
    implementation(libs.kotlinx.serialization.core)

    testImplementation(libs.bundles.kotest)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}
