plugins {
    alias(libs.plugins.jvm)
}

group = "com.example.rest_hateoas.adapter"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {

    implementation(project(":application"))
    implementation("io.github.xn32:json5k:0.3.0")
    implementation("org.slf4j:slf4j-api:2.0.16")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
