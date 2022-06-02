package kz.app.ui_user_info

sealed class UserAddressEvent {
    data class GetUserInfo(val phoneNumber: String) : UserAddressEvent()
    object GetAllCities : UserAddressEvent()
    object OnBackPressed : UserAddressEvent()
    object RemoveHeadFromQueue : UserAddressEvent()
    object SetUserAddress : UserAddressEvent()

    data class UpdateCity(val city: String) : UserAddressEvent()
    data class UpdateAddress(val address: String) : UserAddressEvent()
}
