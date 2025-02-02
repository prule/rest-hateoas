// https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html
// https://medium.com/@ykakdas/using-buildsrc-in-an-android-project-for-easier-dependency-management-with-kotlin-dsl-3b71c25d6d1

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
    // need to do it this way so we can use the version catalog
    // see settings.gradle.kts which loads the version catalog
    //    https://discuss.gradle.org/t/using-version-catalog-from-buildsrc-buildlogic-xyz-common-conventions-scripts/48534/7
    implementation(versionCatalogs.named("libs").findLibrary("kotlin-gradle-plugin").orElseThrow(::AssertionError))

    implementation(
        versionCatalogs.named("libs").findLibrary("serialization-gradle-plugin").orElseThrow(::AssertionError)
    )


    implementation(
        versionCatalogs.named("libs").findLibrary("kapt-gradle-plugin").orElseThrow(::AssertionError)
    )


}
