package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SetUserAddressBody(
    val phoneNumber: String,
    val city: String,
    val address: String
)
