plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.spotless)
    alias(libs.plugins.versions)
}

group = "fi.apinkivi.common"
description = "Common domain independent code."

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.serialization)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

spotless {
    isEnforceCheck = false

    fun com.diffplug.gradle.spotless.FormatExtension.targetExtension(extension: String) {
        target("**/*.$extension")
        targetExclude(
            "**/build/**",
            "**/.gradle/**",
            "**/.kotlin/**",
        )
    }
    kotlin {
        targetExtension("kt")
        ktlint()
    }

    kotlinGradle {
        targetExtension("gradle.kts")
        ktlint()
    }
}

tasks.named("check") {
    dependsOn("detektMainJvm", "detektTestJvm")
}
