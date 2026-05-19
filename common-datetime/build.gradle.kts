plugins {
    kotlin("multiplatform")
    id("dev.detekt")
    id("org.jetbrains.dokka")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.datetime)
        }
    }
}
