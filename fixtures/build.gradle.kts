plugins {
    id("shared")
}

group = "com.example.rest_hateoas.fixtures"
version = "0.0.1-SNAPSHOT"

dependencies {

    implementation(project(":application"))

    implementation(libs.json5k)
    implementation(libs.slf4j.api)
}
