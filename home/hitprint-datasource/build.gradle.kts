apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id(SqlDelight.plugin)
}

dependencies {
    "implementation"(project(Modules.hitprintDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.cio)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.contentNegotiation)
    "implementation"(Ktor.android)
    "implementation"(Ktor.logging)
}

sqldelight {
    database("HitprintDatabase") {
        packageName = "kz.app.hitprint_datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}