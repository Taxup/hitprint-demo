package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SelectServicesBody(
    val orderNumber: String,
    val serviceIds: List<Long>
)
