package com.teb.wpcore.pn

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.ui.screen.main.MainActivity


class PnService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()

    }


    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    override fun onNewToken(token: String) {
//        Log.d(TAG, "Refreshed token: $token")

        Log.d("FCM", "Refreshed token : $token")


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }

    val gson = Gson()
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val pushData = PushData(
            action = message.data["action"],
            text = message.data["text"],
            url = message.data["url"],
        )

        if (pushData.action.equals("open_url")) {

            val notificationIntent = Intent(this, MainActivity::class.java)

            notificationIntent.action = "android.intent.action.VIEW"
            notificationIntent.data = Uri.parse(pushData.url)


            //  RxBus.publish(RxBus.SUBJECT_MY_SUBJECT,intent.getExtras());
            var flags = PendingIntent.FLAG_UPDATE_CURRENT

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags = flags or PendingIntent.FLAG_IMMUTABLE
            }

            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, flags)


            var soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(this, "TEB")
                    .setSmallIcon(com.teb.wpcore.R.drawable.ic_baseline_arrow_back_24)
                    .setContentTitle(getString(com.onesignal.R.string.app_name))
                    .setContentText(pushData.text)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(pushData.text))
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent)

            var notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name: CharSequence = "name"
                val description = "CEPTETEB Notification"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("TEB", name, importance)
                channel.description = description
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(1, notificationBuilder.build())


        } else if (pushData.action.equals("change_logo")) {
            val persistance = ServiceLocator.providePersistance();
            pushData.url?.let { persistance.setCustomLogo(this, it) }
        }


        Log.d("ahmet", "Notification Received")
    }


}