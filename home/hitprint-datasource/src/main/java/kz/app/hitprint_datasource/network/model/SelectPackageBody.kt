package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SelectPackageBody(
    val orderNumber: String,
    val packageId: Long
)
