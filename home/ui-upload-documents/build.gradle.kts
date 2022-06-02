apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.hitprintDomain))
    "implementation"(project(Modules.hitprintInteractors))
    "implementation"(project(Modules.navigation))

}