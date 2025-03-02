import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.example.data_local"
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
    implementation(libs.androidx.datastore)
    implementation(libs.protobuf.javalite)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    testImplementation(libs.unit.test.junit)
    testImplementation(libs.unit.test.coroutines)
    testImplementation(libs.unit.test.mockito.kotlin)
}

protobuf {
    protoc {
        artifact = libs.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}