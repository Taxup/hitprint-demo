package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpBody(
    val phoneNumber: String
)
