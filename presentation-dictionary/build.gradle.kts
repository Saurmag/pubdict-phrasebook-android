plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.presentation_dictionary"
    compileSdk = Config.defaultCompileSdkVersion

    defaultConfig {
        minSdk = Config.defaultMinSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    flavorDimensions += "originLanguage"
    productFlavors {
        create("lezgin") {
            dimension = "originLanguage"
            buildConfigField(type = "String", name = "originLanguageIso", value = "\"lez\"")
        }
        create("avar") {
            dimension = "originLanguage"
            buildConfigField(type = "String", name = "originLanguageIso", value = "\"ava\"")
        }
        create("kumyk") {
            dimension = "originLanguage"
            buildConfigField(type = "String", name = "originLanguageIso", value = "\"kum\"")
        }
        create("tabasaran") {
            dimension = "originLanguage"
            buildConfigField(type = "String", name = "originLanguageIso", value = "\"tab\"")
        }
        create("dargin") {
            dimension = "originLanguage"
            buildConfigField(type = "String", name = "originLanguageIso", value = "\"dar\"")
        }
        create("lakz") {
            dimension = "originLanguage"
            buildConfigField(type = "String", name = "originLanguageIso", value = "\"lak\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11.target
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(project(path = ":presentation-common"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.serialization)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.compose.paging)
    implementation(libs.dagger)
    implementation(libs.coil.compose)
    implementation(libs.bundles.orbit)
    kapt(libs.dagger.compiler)
    testImplementation(libs.orbit.test)
    testImplementation(libs.unit.test.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}