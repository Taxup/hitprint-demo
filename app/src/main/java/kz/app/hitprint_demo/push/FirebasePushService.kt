package kz.app.hitprint_demo.push

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kz.app.hitprint_interactors.SetNewToken
import kz.app.utils.PhoneDataStore
import javax.inject.Inject

@AndroidEntryPoint
class FirebasePushService : FirebaseMessagingService() {

    @Inject
    lateinit var setNewToken: SetNewToken
    @Inject
    lateinit var dataStore: PhoneDataStore
    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.Main).launch {
            val phone = "+7${dataStore.getPhone.first()}"
            setNewToken.execute(phone, token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notificationManager.notify(message)
    }

}