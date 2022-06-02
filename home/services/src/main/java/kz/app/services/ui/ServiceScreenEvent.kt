package kz.app.services.ui

sealed class ServiceScreenEvent {

    object GetAllServices : ServiceScreenEvent()

    data class UpdateServiceName(val serviceName: String) : ServiceScreenEvent()

    object FilterServices : ServiceScreenEvent()

    object OnRemoveHeadFromQueue : ServiceScreenEvent()

    data class SelectService(val serviceId: Long) : ServiceScreenEvent()

    object OnBackPressed : ServiceScreenEvent()

    object OnNextClicked : ServiceScreenEvent()
}
