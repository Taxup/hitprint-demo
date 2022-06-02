package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserBody(
    @SerialName("number")
    val number: String,
    @SerialName("token")
    val token: String
)
