apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.utils))
    "implementation"(project(Modules.navigation))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.hitprintInteractors))
    "implementation"(project(Modules.firebaseAuth))
    "implementation"(Firebase.auth)
    "implementation"(Google.auth)
    "implementation"(Google.authPhone)


}