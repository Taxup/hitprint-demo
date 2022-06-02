package kz.app.hitprint_datasource.network.model

import kz.app.hitprint_domain.Center
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CenterDto(

    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String?,

    @SerialName("address")
    val address: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("workTime")
    val workTime: String,

    @SerialName("website")
    val website: String?,

    @SerialName("rating")
    val rating: Float,

    @SerialName("rateNumber")
    val rateNumber: Int,

    @SerialName("city")
    val city: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double
)

fun CenterDto.toDomain() = Center(
    id = id,
    name = name,
    description = description,
    address = address,
    phone = phone,
    workTime = workTime,
    website = website,
    rating = rating,
    rateNumber = rateNumber,
    city = city,
    latitude = latitude,
    longitude = longitude,
)