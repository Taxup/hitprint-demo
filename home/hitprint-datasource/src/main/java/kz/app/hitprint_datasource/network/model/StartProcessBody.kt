package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class StartProcessBody(
    val centerId: Long,
    val customerPhone: String,
)
