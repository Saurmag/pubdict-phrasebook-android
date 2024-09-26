plugins {
    id(Plugin.Android.library)
    id(Plugin.Kotlin.android)
    id(Plugin.Kotlin.kapt)
}

android {
    namespace = "com.example.data_repository"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(project(path = ":data-repository"))
    implementation(Dependency.Coroutines.core)
    implementation(Dependency.Persistence.dataStore)
    implementation(Dependency.DI.dagger)
    kapt(Dependency.DI.daggerCompiler)
    testImplementation(Dependency.Test.junit)
    testImplementation(Dependency.Test.coroutines)
    testImplementation(Dependency.Test.mockitoKotlin)
}