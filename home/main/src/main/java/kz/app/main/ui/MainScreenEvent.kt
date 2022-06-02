package kz.app.main.ui

import android.app.Activity
import android.content.Context
import kz.app.hitprint_domain.Center

sealed class MainScreenEvent {

    data class GetPrintPointsLocations(val city: String) : MainScreenEvent()

    object GetUserInfo : MainScreenEvent()

    data class GetPrintPointInfo(val pointId: Long) : MainScreenEvent()

    object OnRemoveHeadFromQueue : MainScreenEvent()

    data class OnCenterClicked(val center: Center) : MainScreenEvent()

    object OnBackPressed : MainScreenEvent()

    object DetectUserLocation : MainScreenEvent()

    data class EnableGeolocation(val activity: Activity) : MainScreenEvent()

    data class OnLinkClicked(val context: Context,val link: String) : MainScreenEvent()

    data class UpdateCameraZoom(val zoom: Float) : MainScreenEvent()

    object Logout: MainScreenEvent()

}
