plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.data_repository"
    compileSdk = Config.defaultCompileSdkVersion

    defaultConfig {
        minSdk = Config.defaultMinSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11.target
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(project(path = ":data-repository"))
    implementation(libs.coroutines.core)
    implementation(libs.retrofit)
    implementation (libs.retrofit.converter.moshi)
    implementation(libs.moshi)
    kapt(libs.moshi.codegen)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    testImplementation(libs.unit.test.junit)
    testImplementation(libs.unit.test.coroutines)
    testImplementation(libs.unit.test.mockito.kotlin)
}