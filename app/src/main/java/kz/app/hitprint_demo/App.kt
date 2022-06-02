package kz.app.hitprint_demo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAPKIT_KEY)

        initFirebase()
    }

    private fun initFirebase() {
        val firebaseApp = FirebaseApp.initializeApp(this)
        firebaseApp?.let {
            val firebaseAppCheck = FirebaseAppCheck.getInstance(it)
            firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance()
            )
        }
    }
}
