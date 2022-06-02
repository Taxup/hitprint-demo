package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable
import kz.app.hitprint_domain.Review

@Serializable
data class RatingDto(
    val userPhone: String,
    val businessId: Long,
    val rating: Float,
    val comment: String,
    val dateCreated: String? = null,
)

@Serializable
data class CreateUpdateRatingBody(
    val orderNumber: String,
    val rating: Int,
    val comment: String
)


fun RatingDto.toDomain() = Review(
    userPhone = userPhone,
    businessId = businessId,
    rating = rating,
    comment = comment,
    dateCreated = dateCreated,
)