plugins {
    id(Plugin.Android.library)
    id(Plugin.Kotlin.android)
    id(Plugin.Kotlin.kapt)
}

android {
    namespace = "com.example.presentation_common"
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
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = Config.defaultSourceCompatibility
        targetCompatibility = Config.defaultTargetCompatibility
    }
    kotlinOptions {
        jvmTarget = Config.defaultJVMTarget
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(Dependency.Coroutines.android)
    implementation(platform(Dependency.Compose.bom))
    implementation(Dependency.Compose.ui)
    implementation(Dependency.Compose.material)
    implementation(Dependency.Compose.viewModel)
    implementation(Dependency.Compose.uiGraphics)
    implementation(Dependency.Compose.uiToolingPreview)
    implementation(Dependency.AndroidX.lifecycle)
    implementation(Dependency.Navigation.composeNavigation)
    implementation(Dependency.DI.dagger)
    kapt(Dependency.DI.daggerCompiler)
    testImplementation(Dependency.Test.junit)
    androidTestImplementation(Dependency.AndroidTest.junit)
}