object Dependency {
    object AndroidX {
        const val core = "androidx.core:core-ktx:${Version.coreKtx}"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleRuntimeKtx}"
        const val activityCompose = "androidx.activity:activity-compose:${Version.activityCompose}"
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:${Version.composeBom}"
        const val ui = "androidx.compose.ui:ui"
        const val uiGraphics = "androidx.compose.ui:ui-graphics"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val material = "androidx.compose.material3:material3"
    }

    object Navigation {
        const val composeNavigation = "androidx.navigation:navigation-compose:${Version.navigation}"
    }

    object Coroutines {
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
        const val moshi = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi}"
        const val okHttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
    }

    object Persistence {
        const val room = "androidx.room:room-runtime:${Version.room}"
        const val roomKtx = "androidx.room:room-ktx:${Version.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
    }

    object DI {
        const val dagger = "com.google.dagger:dagger:${Version.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Version.dagger}"
    }

    object Test {
        const val junit = "junit:junit:${Version.junit}"
        const val mockito = "org.mockito:mockito-inline:${Version.mockito}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"
    }

    object AndroidTest {
        const val uiTestJUnit4 = "androidx.compose.ui:ui-test-junit4"
        const val junit = "androidx.test.ext:junit:${Version.espressoJunit}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espressoCore}"
        const val core = "androidx.test:core:${Version.androidTestCore}"
    }

    object Debug {
        const val uiTooling = "androidx.compose.ui:ui-tooling"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"
    }
}