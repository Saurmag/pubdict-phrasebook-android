plugins {
    id(Plugin.Android.library)
    id(Plugin.Kotlin.android)
}

android {
    namespace = "com.example.domain"
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
        sourceCompatibility = Config.defaultSourceCompatibility
        targetCompatibility = Config.defaultTargetCompatibility
    }
    kotlinOptions {
        jvmTarget = Config.defaultJVMTarget
    }
}

dependencies {
    implementation(Dependency.Coroutines.core)
    testImplementation(Dependency.Test.junit)
    testImplementation(Dependency.Test.coroutines)
    testImplementation(Dependency.Test.mockitoKotlin)
}