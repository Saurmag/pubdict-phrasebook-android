// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}

//subprojects {
//    tasks.withType<KotlinCompile>().configureEach {
//        compilerOptions {
//            if (project.findProperty("enableMultiModuleComposeReports") == "true") {
//                freeCompilerArgs.addAll(
//                    listOf(
//                        "-P",
//                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
//
//                                project.buildDir.absolutePath + "/compose_metrics"
//                    )
//                )
//                freeCompilerArgs.addAll(
//                    listOf(
//                        "-P",
//
//                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
//
//                                project.buildDir.absolutePath + "/compose_metrics"
//                    )
//                )
//            }
//        }
//
//    }
//
//}
