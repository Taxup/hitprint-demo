apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.hitprintDomain))
    "implementation"(project(Modules.hitprintDataSource))
    "implementation"(Kotlinx.coroutinesCore)
    "implementation"(Ktor.core)
}