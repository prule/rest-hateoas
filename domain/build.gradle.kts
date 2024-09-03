

//https://spring.io/guides/tutorials/spring-boot-kotlin
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.kapt)
    alias(libs.plugins.serialization)

}

repositories {
    mavenCentral()
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
    annotationProcessor("javax.annotation:javax.annotation-api:1.3.2")
// https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.google.guava:guava:33.3.0-jre")
    implementation(libs.logback.classic)
    implementation(libs.bundles.logging)

    testImplementation(libs.bundles.kotest)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

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
