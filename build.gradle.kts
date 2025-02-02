import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.asciidoctor.gradle.jvm.pdf.AsciidoctorPdfTask

plugins {
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

group = "com.example.rest_hateoas"
version = "0.0.1-SNAPSHOT"


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
        "root" to project.rootDir.absolutePath,
        "github" to "https://github.com/prule/rest-hateoas/"
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
