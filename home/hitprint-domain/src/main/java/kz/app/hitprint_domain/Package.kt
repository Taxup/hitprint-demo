package kz.app.hitprint_domain

data class Package(
    val id: Long,
    val name: String,
    val type: PackageType,
    val price: Double,
    val currencyCode: String
)

enum class PackageType {
    STANDARD, ECO,
}