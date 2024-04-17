import org.gradle.api.JavaVersion

object Config {
    const val defaultCompileSdkVersion = 34
    const val defaultMinSdkVersion = 24
    const val defaultTargetSdkVersion = 34
    const val defaultJVMTarget = "17"
    val defaultSourceCompatibility = JavaVersion.VERSION_17
    val defaultTargetCompatibility = JavaVersion.VERSION_17
}