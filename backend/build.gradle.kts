plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("kapt") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "1.5.2"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.0"
}

version = "0.1"
group = "cz.ufnukanazemle"

repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("cz.ufnukanazemle.*")
    }
}

val kotlinVersion: String by project
val arrowVersion: String by project
val micronautVersion: String by project
val r2dbcMigrateVersion: String by project

dependencies {
    implementation(project(":frontend"))
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut.openapi:micronaut-openapi")
    kapt("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-cli:1.3.7")
    implementation("io.micronaut.data:micronaut-data-r2dbc")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.r2dbc:micronaut-r2dbc-core")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.mariadb:r2dbc-mariadb")
    implementation("io.micronaut:micronaut-validation")

    implementation("io.arrow-kt:arrow-core:$arrowVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testAnnotationProcessor("io.micronaut:micronaut-inject-java")
    implementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("name.nkonev.r2dbc-migrate:r2dbc-migrate-core:${r2dbcMigrateVersion}")
    implementation("name.nkonev.r2dbc-migrate:r2dbc-migrate-resource-reader-reflections:${r2dbcMigrateVersion}")
}


application {
    mainClass.set("cz.ufnukanazemle.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

}
kapt {
    arguments {
//        arg("micronaut.openapi.views.spec", "micronaut.openapi.views.spec=swagger-ui.enabled=true,swagger-ui.theme=flattop,swagger-ui.oauth2RedirectUrl=http://localhost:8080/swagger-ui/oauth2-redirect.html,swagger-ui.oauth2.clientId=myClientId,swagger-ui.oauth2.scopes=openid,swagger-ui.oauth2.usePkceWithAuthorizationCodeGrant=true")
        arg("micronaut.openapi.views.spec", "redoc.enabled=true,rapidoc.enabled=true," +
                "swagger-ui.enabled=true," +
                "swagger-ui.theme=flattop," +
                "swagger-ui.oauth2RedirectUrl=BASE_DOMAIN/swagger-ui/oauth2-redirect.html"
        )
    }
}
tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf(
        "-parameters",
        "-Amicronaut.processing.incremental=true",
        "-Amicronaut.processing.annotations=cz.partners.*",
        "-Amicronaut.processing.group=$group",
        "-Amicronaut.processing.module=$name"
    ))
}