buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.sqlDelightGradlePlugin)
        classpath(Build.hiltAndroid)
        classpath(Build.googleServices)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}