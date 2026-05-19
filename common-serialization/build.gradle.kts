plugins {
    kotlin("multiplatform")
    id("dev.detekt")
    id("org.jetbrains.dokka")
}

kotlin {
    jvm()

    sourceSets.commonMain.dependencies {
        api(project(":"))
        api(project(":common-datetime"))
        api(libs.kotlin.serialization.json)
    }
}
