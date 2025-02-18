plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.publicdictionary"
    compileSdk = Config.defaultCompileSdkVersion

    defaultConfig {
        applicationId = "com.example.publicdictionary"
        minSdk = Config.defaultMinSdkVersion
        targetSdk = Config.defaultTargetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
            applicationIdSuffix = ".lezgin"
            versionNameSuffix = "-lezgin"
        }
        create("avar") {
            dimension = "originLanguage"
            applicationIdSuffix = ".avar"
            versionNameSuffix = "-avar"
        }
        create("kumyk") {
            dimension = "originLanguage"
            applicationIdSuffix = ".kumyk"
            versionNameSuffix = "-kumyk"
        }
        create("tabasaran") {
            dimension = "originLanguage"
            applicationIdSuffix = ".tabasaran"
            versionNameSuffix = "-tabasaran"
        }
        create("dargin") {
            dimension = "originLanguage"
            applicationIdSuffix = ".dargin"
            versionNameSuffix = "-dargin"
        }
        create("lakz") {
            dimension = "originLanguage"
            applicationIdSuffix = ".lakz"
            versionNameSuffix = "-lakz"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = Config.defaultJVMTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(project(path = ":data-repository"))
    implementation(project(path = ":data-remote"))
    implementation(project(path = ":data-local"))
    implementation(project(path = ":presentation-common"))
    implementation(project(path = ":presentation-dictionary"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    testImplementation(libs.unit.test.junit)
    androidTestImplementation(libs.androidx.ui.test.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}