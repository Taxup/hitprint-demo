package kz.app.hitprint_domain

data class Review(
    val userPhone: String,
    val businessId: Long,
    val rating: Float,
    val comment: String,
    val dateCreated: String? = null,
)
