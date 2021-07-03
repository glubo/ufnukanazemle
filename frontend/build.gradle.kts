plugins {
    kotlin("multiplatform") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
    id("dev.fritz2.fritz2-gradle") version "0.11"
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(IR) {
        browser()
        this.nodejs()
    }.binaries.executable()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("dev.fritz2:core:0.11")
                // see https://components.fritz2.dev/
                 implementation("dev.fritz2:components:0.11")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
            }
            java {
                sourceCompatibility = JavaVersion.VERSION_11
            }
        }
        val jvmMain by getting {
            dependencies {
            }
            java {
                sourceCompatibility = JavaVersion.VERSION_11
            }
        }
        val jsMain by getting {
            dependencies {
            }
        }
    }
}