package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable
import kz.app.hitprint_domain.User

@Serializable
data class UserDto(
    val phoneNumber: String,
    val city: String? = null,
    val address: String? = null
)

fun UserDto.toDomain() = User(phoneNumber, city, address)

fun User.toDto() = UserDto(phoneNumber, city, address)
