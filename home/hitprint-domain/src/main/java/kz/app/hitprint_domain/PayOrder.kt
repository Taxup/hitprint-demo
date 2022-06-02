package kz.app.hitprint_domain

data class PayOrder(
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

enum class OrderStage {
    SELECT_SERVICES,
    UPLOAD_DOCUMENTS,
    SELECT_PACKAGES,
    SETTING_USER_INFO,
    PRICING,
    READY_FOR_PAY,
    PAID,
    PAYMENT_DONE
}
