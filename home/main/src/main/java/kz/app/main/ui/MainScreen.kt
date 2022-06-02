package kz.app.main.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.NotListedLocation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.launch
import kz.app.components.DefaultBottomSheet
import kz.app.components.DefaultScreen
import kz.app.hitprint_domain.Center
import kz.app.hitprint_domain.Location
import kz.app.main.ui.components.CenterDetails
import kz.app.main.ui.components.MapToolbar
import kz.app.main.utils.Constants
import kz.app.main.utils.addMarker
import kz.app.main.utils.isGpsEnabled
import kz.app.main.utils.moveCamera
import kz.app.utils.PermissionsUtils

@ExperimentalMaterialApi
@Composable
fun MainScreen(
    state: MainScreenState,
    events: (MainScreenEvent) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            if (it.values.all { granted -> granted }) {
                events.invoke(MainScreenEvent.DetectUserLocation)
            }
        }
    )
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    /*
        mapview created in remember scope cause it is android view and it is bad to create new instances at every recomposition
     */
    val mapView = remember {
        MapView(context).apply {
            moveCamera(state.userLocation ?: Location(43.238949, 76.889709), state.zoom)
        }
    }
    /*
        markerList used to check if markers already added to map, not to overload map with same markers
     */
    val markerList = remember { mutableListOf<PlacemarkMapObject>() }
    /*
        MapObjectTapListener has weak reference, to prevent GC collecting it is remembered
     */
    val tapListener = remember {
        MapObjectTapListener { mapObject: MapObject, _: Point ->
            events.invoke(MainScreenEvent.GetPrintPointInfo((mapObject.userData as Center).id))
            coroutineScope.launch { sheetState.show() }
            true
        }
    }
    val userMarker = remember {
        mapView.addMarker(43.238949, 76.889709, isUser = true)
    }


    BackHandler {
        if (sheetState.isVisible) {
            coroutineScope.launch { sheetState.hide() }
        } else {
            events.invoke(MainScreenEvent.OnBackPressed)
        }
    }
    DefaultBottomSheet(
        sheetState = sheetState,
        sheetContent = {
            state.clickedCenter?.let {
                CenterDetails(
                    center = it,
                    reviews = state.centerReviews,
                    reviewsProgress = state.centerReviewsProgress,
                    onCenterSelected = { center ->
                        coroutineScope.launch { sheetState.hide() }
                        events.invoke(MainScreenEvent.OnCenterClicked(center))
                    },
                    onLinkCLiked = { link ->
                        events.invoke(MainScreenEvent.OnLinkClicked(context, link))
                    }
                )
            }
        }
    ) {
        DefaultScreen(
            queue = state.queue,
            progressBarState = state.progressBarState,
            onRemoveHeadFromQueue = {
                events.invoke(MainScreenEvent.OnRemoveHeadFromQueue)
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = Color.White,
                    onClick = {
                        if (PermissionsUtils.isGranted(context, *Constants.permissions)) {
                            if (!isGpsEnabled(context)) {
                                events.invoke(MainScreenEvent.EnableGeolocation(context as Activity))
                            } else {
                                events.invoke(MainScreenEvent.DetectUserLocation)
                                state.userLocation?.let { mapView.moveCamera(it, 15f) }
                            }
                        } else {
                            launcher.launch(Constants.permissions)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isGpsEnabled(context)) Icons.Rounded.LocationOn
                        else Icons.Rounded.NotListedLocation,
                        contentDescription = "LocationOn",
                        tint = Color.Red
                    )
                }
            },
            fabPosition = FabPosition.End
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            ) {
                state.userInfo?.address.let { address ->
                    MapToolbar(address = address, onLogout = { events.invoke(MainScreenEvent.Logout) })
                }
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { mapView }) {
                    mapView.map.addCameraListener { _: Map, cameraPosition: CameraPosition, _: CameraUpdateReason, _: Boolean ->
                        events.invoke(MainScreenEvent.UpdateCameraZoom(cameraPosition.zoom))
                    }
                    if (markerList.isEmpty()) {
                        state.centers?.forEach { point ->
                            val marker = mapView.addMarker(
                                point.latitude,
                                point.longitude,
                                point,
                                tapListener
                            )
                            markerList.add(marker)
                        }
                    }
                    state.userLocation?.let { location ->
                        userMarker.geometry = Point(location.latitude, location.longitude)
                    }
                }
                DisposableEffect(key1 = "lifecycle", effect = {
                    mapView.onStart()
                    onDispose { mapView.onStop() }
                })
            }
        }
    }

}