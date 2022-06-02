package kz.app.hitprint_domain

data class Service(
    val type: ServiceType,
    val list: List<Document>
)
