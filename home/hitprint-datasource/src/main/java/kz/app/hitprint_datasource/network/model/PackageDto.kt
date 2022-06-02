package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable
import kz.app.hitprint_domain.Package
import kz.app.hitprint_domain.PackageType

@Serializable
data class PackageDto(
    val id: Long,
    val name: String,
    val type: PackageType,
    val price: Double,
    val currencyCode: String
)

fun PackageDto.toDomain() = Package(id, name, type, price, currencyCode)