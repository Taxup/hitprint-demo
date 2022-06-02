package kz.app.hitprint_domain

data class PrintService(
    val id: Long,
    val name: String,
    val imageSrc: String,
    val price: Double,
    val currencyCode: String,
    val type: ServiceType,
    val selected: Boolean = false
)
