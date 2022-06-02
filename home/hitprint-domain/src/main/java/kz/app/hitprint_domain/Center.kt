package kz.app.hitprint_domain

data class Center(
    val id: Long,
    val name: String,
    val description: String?,
    val address: String,
    val phone: String,
    val workTime: String,
    val website: String?,
    val rating: Float,
    val rateNumber: Int,
    val city: String,
    val latitude: Double,
    val longitude: Double
)