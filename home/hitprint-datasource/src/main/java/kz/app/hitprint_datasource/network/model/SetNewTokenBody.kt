package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SetNewTokenBody(
    val phoneNumber: String,
    val token: String
)
