package kz.app.main.utils

import android.content.Context
import android.location.LocationManager
import android.view.View
import androidx.core.content.ContextCompat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import kz.app.hitprint_domain.Center
import kz.app.hitprint_domain.Location
import kz.app.main.R


fun MapView.moveCamera(location: Location, zoom: Float) {
    map.move(
        CameraPosition(Point(location.latitude, location.longitude), zoom, 0.0f, 0.0f),
        Animation(Animation.Type.SMOOTH, 0.2F),
        null
    )
}

fun MapView.addMarker(
    latitude: Double,
    longitude: Double,
    userData: Center? = null,
    onTapListener: MapObjectTapListener? = null,
    isUser: Boolean = false
): PlacemarkMapObject {
    val markerIcon = View(context).apply {
        background = ContextCompat.getDrawable(
            context,
            if (isUser) R.drawable.ic_my_location else R.drawable.ic_printshop
        )
    }
    val marker = map.mapObjects.addPlacemark(
        Point(latitude, longitude),
        ViewProvider(markerIcon)
    )
    marker.userData = userData
    onTapListener?.let { marker.addTapListener(it) }
    return marker
}

fun isGpsEnabled(context: Context): Boolean {
    val manager: LocationManager? = ContextCompat.getSystemService(context, LocationManager::class.java)
    return manager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
}