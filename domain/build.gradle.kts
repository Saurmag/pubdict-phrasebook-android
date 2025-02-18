plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.coroutines.core)
    testImplementation(libs.unit.test.junit)
    testImplementation(libs.unit.test.coroutines)
    testImplementation(libs.unit.test.mockito.kotlin)
}