plugins {
    id("shared")
}

group = "com.example.rest_hateoas.fixtures"
version = "0.0.1-SNAPSHOT"

dependencies {

    implementation(project(":application"))
    implementation("io.github.xn32:json5k:0.3.0")
    implementation("org.slf4j:slf4j-api:2.0.16")

}
