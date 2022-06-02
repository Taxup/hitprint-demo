package kz.app.hitprint_datasource.network.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class AppException(override val message: String) : Exception(message)