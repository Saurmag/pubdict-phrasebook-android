plugins {
    id(Plugin.Android.library)
    id(Plugin.Kotlin.android)
    id(Plugin.Kotlin.kapt)
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
        sourceCompatibility = Config.defaultSourceCompatibility
        targetCompatibility = Config.defaultTargetCompatibility
    }
    kotlinOptions {
        jvmTarget = Config.defaultJVMTarget
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(Dependency.Coroutines.core)
    implementation(Dependency.DI.dagger)
    kapt(Dependency.DI.daggerCompiler)
    testImplementation(Dependency.Test.junit)
    testImplementation(Dependency.Test.coroutines)
    testImplementation(Dependency.Test.mockitoKotlin)
}