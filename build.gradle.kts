import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.asciidoctor.gradle.jvm.pdf.AsciidoctorPdfTask


//https://spring.io/guides/tutorials/spring-boot-kotlin
plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    alias(libs.plugins.jvm)
    alias(libs.plugins.spring)
    alias(libs.plugins.jpa)
    alias(libs.plugins.allopen)
    alias(libs.plugins.kapt)
    alias(libs.plugins.serialization)

    alias(libs.plugins.asciidoctor.pdf)
    alias(libs.plugins.asciidoctor.convert)
    alias(libs.plugins.asciidoctor.epub)
    alias(libs.plugins.asciidoctor.gems)
}

repositories {
    mavenCentral()
    ruby {
        gems()
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
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

tasks.register("generateDocs") {
    dependsOn("asciidoctor", "asciidoctorPdf")
    group = "documentation"
    description = "Generates both HTML and PDF documentation"
}

// tag::asciidoctor-gradle-configuration[]
tasks {

    val asciidocAttributes = mapOf(
        // define a custom attribute to be used in the document eg as {source}
        // unfortunately these won't work in the intellij preview, only in the gradle output
        // so you would need to separately define these attributes in the intellij settings
        "main_source" to project.sourceSets.main.get().kotlin.srcDirs.first(),
        "test_source" to project.sourceSets.test.get().kotlin.srcDirs.first(),
    )

    "asciidoctor"(AsciidoctorTask::class) {
        baseDirFollowsSourceDir()
        attributes(asciidocAttributes)
    }
    "asciidoctorPdf"(AsciidoctorPdfTask::class) {
        baseDirFollowsSourceDir()
        attributes(asciidocAttributes)
    }
}
// end::asciidoctor-gradle-configuration[]

dependencies {

    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jpa")
    annotationProcessor("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
    annotationProcessor("javax.annotation:javax.annotation-api:1.3.2")

    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    implementation("com.google.guava:guava:33.2.1-jre")
    implementation("io.github.xn32:json5k:0.3.0")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
    implementation(libs.logback.classic)
    implementation(libs.bundles.logging)

    testImplementation(libs.bundles.kotest)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    runtimeOnly("com.h2database:h2")

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
