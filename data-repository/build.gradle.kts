plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
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
    implementation(project(path = ":domain"))
    implementation(libs.coroutines.core)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    testImplementation(libs.unit.test.junit)
    testImplementation(libs.unit.test.coroutines)
    testImplementation(libs.unit.test.mockito.kotlin)
}