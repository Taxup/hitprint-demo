package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable
import kz.app.hitprint_domain.PrintService
import kz.app.hitprint_domain.ServiceType

@Serializable
data class PrintServiceDto(
    val id: Long,
    val name: String,
    val imageSrc: String,
    val price: Double,
    val currencyCode: String,
    val type: ServiceType
)

fun PrintServiceDto.toDomain() = PrintService(id, name, imageSrc, price, currencyCode, type)
