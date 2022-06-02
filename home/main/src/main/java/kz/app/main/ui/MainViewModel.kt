package kz.app.main.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.app.core.domain.DataState
import kz.app.core.domain.Logger
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.firebase_auth.FirebaseAuthenticator
import kz.app.hitprint_domain.Center
import kz.app.hitprint_domain.Location
import kz.app.hitprint_interactors.*
import kz.app.navigation.HomeScreen
import kz.app.navigation.Navigator
import kz.app.components.R
import kz.app.navigation.Screen
import kz.app.utils.PhoneDataStore
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val getCenters: GetCenters,
    private val selectCenter: SelectCenter,
    private val getUserInfo: GetUserInfo,
    private val userLogout: UserLogout,
    private val getCenterReviews: GetCenterReviews,
    private val dataStore: PhoneDataStore,
    private val logger: Logger,
    private val navigator: Navigator,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val state: MutableState<MainScreenState> = mutableStateOf(MainScreenState())

    private var locationRequest: LocationRequest = LocationRequest.create()
    private var builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

    init {
        onTriggerEvent(MainScreenEvent.GetUserInfo)
        onTriggerEvent(MainScreenEvent.DetectUserLocation)

        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (30 * 1000).toLong()
        locationRequest.fastestInterval = (5 * 1000).toLong()
        builder.setAlwaysShow(true)
    }

    fun onTriggerEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.GetPrintPointsLocations -> getPrintPointLocations(event.city)
            MainScreenEvent.GetUserInfo -> getUserInfo()
            MainScreenEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is MainScreenEvent.GetPrintPointInfo -> printClicked(event.pointId)
            is MainScreenEvent.OnCenterClicked -> onCenterClicked(event.center)
            MainScreenEvent.OnBackPressed -> navigator.navigateUp()
            MainScreenEvent.DetectUserLocation -> detectUserLocation()
            is MainScreenEvent.EnableGeolocation -> enableGeolocation(event.activity)
            MainScreenEvent.Logout -> onLogoutClicked()
            is MainScreenEvent.OnLinkClicked -> onLinkClicked(event.context, event.link)
            is MainScreenEvent.UpdateCameraZoom -> state.value = state.value.copy(zoom = event.zoom)
        }
    }

    private fun onLogoutClicked() {
        appendToMessageQueue(
            UIComponent.Dialog(
                description = context.getString(R.string.exit_text),
                positive = context.getString(R.string.shared_yes),
                positiveAction = { logout() },
                negative = context.getString(R.string.shared_no),
            )
        )
    }

    private fun onLinkClicked(context: Context, link: String) {
        if (link.contains("@")) {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(link))
            i.putExtra(Intent.EXTRA_SUBJECT, "Пишу из Hitprint")
            i.putExtra(Intent.EXTRA_TEXT, "Здравствуйте! ")
            try {
                context.startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    "There are no email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("http://$link")
            context.startActivity(i)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            val phone = "+7${dataStore.getPhone.first()}"
            userLogout.execute(phoneNumber = phone).onEach { dataState ->
                when (dataState) {
                    is DataState.Data -> {
                        dataStore.clearSession()
                        FirebaseAuthenticator.logout()
                        navigator.navigateTo(Screen.Login, popUpTo = HomeScreen.Map, inclusive = true)
                    }
                    is DataState.Loading -> {
                        state.value = state.value.copy(progressBarState = dataState.progressBarState)
                    }
                    is DataState.Response -> {
                        when (dataState.uiComponent) {
                            is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                            is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun enableGeolocation(activity: Activity) {
        val result = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())
        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            val resolvable: ResolvableApiException = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(activity, 0)
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        }
    }

    private fun detectUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(
            locationRequest,
            object: LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)

                    LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(this)

                    if (locationResult != null && locationResult.locations.size > 0) {
                        val location = locationResult.locations.last()
                        state.value = state.value.copy(
                            userLocation = Location(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        )
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            val phone = "+7${dataStore.getPhone.first()}"
            getUserInfo.execute(phoneNumber = phone).onEach { dataState ->
                when (dataState) {
                    is DataState.Data -> {
                        state.value = state.value.copy(userInfo = dataState.data)
                        onTriggerEvent(MainScreenEvent.GetPrintPointsLocations(dataState.data?.city ?: "Алматы"))
                    }
                    is DataState.Loading -> {}
                    is DataState.Response -> {
                        when (dataState.uiComponent) {
                            is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                            is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun onCenterClicked(center: Center) {
        viewModelScope.launch {
            val phone = "+7${dataStore.getPhone.first()}"
            selectCenter.execute(
                centerId = center.id,
                phoneNumber = phone
            ).onEach { dataState ->
                when (dataState) {
                    is DataState.Data -> {
                        state.value = state.value.copy(clickedCenter = null)
                        navigator.navigateTo(Screen.Service, dataState.data?.number)
                    }
                    is DataState.Loading -> state.value =
                        state.value.copy(progressBarState = dataState.progressBarState)
                    is DataState.Response -> {
                        when (dataState.uiComponent) {
                            is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                            is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getPrintPointLocations(city: String) {
        getCenters.execute(city).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(
                        centers = dataState.data ?: emptyList()
                    )
                }
                is DataState.Loading -> state.value =
                    state.value.copy(progressBarState = dataState.progressBarState)
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun printClicked(id: Long) {
        state.value = state.value.copy(clickedCenter = state.value.centers?.find { it.id == id })
        val businessId = state.value.clickedCenter?.id ?: return
        getCenterReviews.execute(businessId).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(centerReviews = dataState.data ?: emptyList())
                }
                is DataState.Loading -> state.value =
                    state.value.copy(centerReviewsProgress = dataState.progressBarState)
                is DataState.Response -> {
                    state.value = state.value.copy(centerReviews = emptyList())
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.queue
        queue.add(uiComponent)
        state.value = state.value.copy(queue = Queue())
        state.value = state.value.copy(queue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.queue
            queue.remove()
            state.value = state.value.copy(queue = Queue())
            state.value = state.value.copy(queue = queue)
        } catch (e: Exception) {
            logger.log(e.message.toString())
        }
    }

}