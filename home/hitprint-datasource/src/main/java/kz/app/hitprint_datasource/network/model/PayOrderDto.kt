package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable
import kz.app.hitprint_domain.OrderStage
import kz.app.hitprint_domain.PayOrder
import kz.app.hitprint_domain.PaymentMethod

@Serializable
data class PayOrderDto(
    val id: Long,

    val number: String,
    val stage: OrderStage,
    val startDate: String,
    val price: Double? = null,
    val executionTime: String? = null,

    val customerPhone: String,
    val deliveryAddress: String? = null,
    val city: String? = null,
    val paymentMethod: PaymentMethod? = null,

    val businessId: Long,
    val businessPhone: String,
    val businessName: String,
    val businessAddress: String,
    val kaspiQrUrl: String? = null,

    val serviceIds: List<Long>? = null,
    val documentUuids: List<String>? = null,
    val packageId: Long? = null
)

fun PayOrderDto.toDomain() = PayOrder(
    id = id,
    number = number,
    stage = stage,
    startDate = startDate,
    price = price,
    executionTime = executionTime,
    customerPhone = customerPhone,
    deliveryAddress = deliveryAddress,
    businessId = businessId,
    city = city,
    paymentMethod = paymentMethod,
    businessPhone = businessPhone,
    businessName = businessName,
    businessAddress = businessAddress,
    serviceIds = serviceIds,
    documentUuids = documentUuids,
    packageId = packageId,
    kaspiQrUrl = kaspiQrUrl
)