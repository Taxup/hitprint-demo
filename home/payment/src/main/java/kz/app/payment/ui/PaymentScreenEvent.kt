package kz.app.payment.ui

sealed class PaymentScreenEvent {

    data class GetOrderState(val orderNumber: String) : PaymentScreenEvent()

    object OnRemoveHeadFromQueue : PaymentScreenEvent()

    object OnBackPressed : PaymentScreenEvent()

    object RateCenter : PaymentScreenEvent()
}
