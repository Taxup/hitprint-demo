package kz.app.hitprint_domain

data class User(
    val phoneNumber: String,
    val city: String? = null,
    val address: String? = null
)
