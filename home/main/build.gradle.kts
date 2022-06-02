apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.navigation))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.utils))
    "implementation"(project(Modules.firebaseAuth))
    "implementation"(project(Modules.hitprintDomain))
    "implementation"(project(Modules.hitprintInteractors))

    "implementation"(Yandex.map)

    "implementation"(Accompanist.pager)

    // If using indicators, also depend on
    "implementation"(Accompanist.pagerIndicators)

}