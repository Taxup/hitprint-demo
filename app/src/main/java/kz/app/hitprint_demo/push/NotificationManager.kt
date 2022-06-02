package kz.app.hitprint_demo.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleObserver
import com.google.firebase.messaging.RemoteMessage
import kz.app.hitprint.MainActivity
import kz.app.hitprint.R
import kz.app.hitprint.utils.Constants
import kotlin.random.Random

class NotificationManager(
    private val context: Context
) : LifecycleObserver {

    companion object {
        private const val channelId = "CHANNEL_ID"
        private const val channelName = "IMPORTANT_NOTIFICATION_CHANNEL"
    }

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private lateinit var notificationBuilder: NotificationCompat.Builder

    private var notifyEnabled = true

    private fun setup(message: RemoteMessage) {
        notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(message.notification?.title ?: context.getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setGroup("hitprint")
            .setSmallIcon(R.drawable.logok)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(message.notification?.body)
            )
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val adminChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(adminChannel)
        }
    }

    fun notify(message: RemoteMessage) {
        setup(message)
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("orderNumber", message.data["orderNumber"])
            action = Constants.PUSH_ACTION
        }
//        if (isOnForeground) {
//            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
//        } else {
            notify(intent)
//        }
    }

    private fun notify(intent: Intent) {
        val flags =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            else
                PendingIntent.FLAG_UPDATE_CURRENT

        val notificationId = Random.nextInt(Int.MAX_VALUE)
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            flags
        )
        notificationBuilder.setContentIntent(pendingIntent)
        if (notifyEnabled) {
            notificationManager.notify(notificationId, notificationBuilder.build())
        }
    }


}