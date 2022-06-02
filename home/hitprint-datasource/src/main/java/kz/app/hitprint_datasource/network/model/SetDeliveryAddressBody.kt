package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable
import kz.app.hitprint_domain.PaymentMethod

@Serializable
data class SetDeliveryAddressBody(
    val orderNumber: String,
    val city: String,
    val address: String,
    val paymentMethod: PaymentMethod
)
