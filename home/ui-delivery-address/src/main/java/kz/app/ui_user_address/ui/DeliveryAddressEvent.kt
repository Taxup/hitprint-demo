package kz.app.ui_user_address.ui

import kz.app.hitprint_domain.PaymentMethod

sealed class DeliveryAddressEvent {
    data class GetUserInfo(val phoneNumber: String) : DeliveryAddressEvent()
    object GetAllCities : DeliveryAddressEvent()
    object OnBackPressed : DeliveryAddressEvent()
    object RemoveHeadFromQueue : DeliveryAddressEvent()
    object GoNext : DeliveryAddressEvent()

    data class UpdateCity(val city: String) : DeliveryAddressEvent()
    data class UpdateAddress(val address: String) : DeliveryAddressEvent()
    data class UpdatePaymentType(val paymentMethod: PaymentMethod) : DeliveryAddressEvent()
}
