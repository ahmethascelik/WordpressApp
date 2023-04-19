package com.teb.wpcore.pn

import android.R
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
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

        if(pushData.action.equals("open_url")){

            val notificationIntent = Intent(this, MainActivity::class.java)

            notificationIntent.action = "android.intent.action.VIEW"
            notificationIntent.data = Uri.parse(pushData.url)


            val contentIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.getActivity(this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            } else {
                PendingIntent.getActivity(this,
                    0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val b = NotificationCompat.Builder(this)

            b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_delete)
                .setContentText(pushData.text)
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
//                .setContentIntent(contentIntent)
                .setContentInfo("Info")


            val notificationManager =
                this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, b.build())
        }else if ( pushData.action.equals("change_logo")){
            val persistance = ServiceLocator.providePersistance();
            pushData.url?.let { persistance.setCustomLogo(this, it) }
        }


        Log.d("ahmet", "Notification Received")
    }


}