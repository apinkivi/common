plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("dev.detekt")
    id("org.jetbrains.dokka")
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    jvm()

    sourceSets.commonMain.dependencies {
        api(project(":"))
    }
}

tasks.named("check") {
    dependsOn("detektMainJvm")
}
