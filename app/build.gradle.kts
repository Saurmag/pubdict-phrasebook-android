plugins {
    id(Plugin.Android.application)
    id(Plugin.Kotlin.android)
    id(Plugin.Kotlin.kapt)
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
    compileOptions {
        sourceCompatibility = Config.defaultSourceCompatibility
        targetCompatibility = Config.defaultTargetCompatibility
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

    implementation(Dependency.AndroidX.core)
    implementation(Dependency.AndroidX.lifecycle)
    implementation(Dependency.AndroidX.activityCompose)
    implementation(platform(Dependency.Compose.bom))
    implementation(Dependency.Navigation.composeNavigation)
    implementation(Dependency.Compose.ui)
    implementation(Dependency.Compose.uiGraphics)
    implementation(Dependency.Compose.uiToolingPreview)
    implementation(Dependency.Compose.material)

    implementation(Dependency.Network.moshi)
    implementation (Dependency.Network.retrofit)
    implementation(Dependency.Network.moshiConverter)
    kapt(Dependency.Network.moshiCodegen)

    testImplementation(Dependency.Test.junit)
    androidTestImplementation(Dependency.AndroidTest.junit)
    androidTestImplementation(Dependency.AndroidTest.espressoCore)
    androidTestImplementation(platform(Dependency.Compose.bom))
    androidTestImplementation(Dependency.AndroidTest.uiTestJUnit4)
    debugImplementation(Dependency.Debug.uiTooling)
    debugImplementation(Dependency.Debug.uiTestManifest)
}