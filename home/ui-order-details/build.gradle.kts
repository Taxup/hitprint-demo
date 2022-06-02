apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.navigation))
    "implementation"(project(Modules.utils))
    "implementation"(project(Modules.hitprintInteractors))
    "implementation"(project(Modules.hitprintDomain))

}