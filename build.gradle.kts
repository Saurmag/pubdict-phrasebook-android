// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugin.Android.application) version Version.androidGradlePlugin apply false
    id(Plugin.Kotlin.android) version Version.kotlin apply false
    id(Plugin.Kotlin.jvm) version Version.kotlin apply false
}