package kz.app.ui_packages.ui

sealed class PackagesEvent {

    data class SelectPackage(val packageId: Long): PackagesEvent()

    object GetAllAvailablePackages: PackagesEvent()

    object OnBackPressed : PackagesEvent()

    object OnRemoveHeadFromQueue : PackagesEvent()

    object OnNextClicked : PackagesEvent()

}
