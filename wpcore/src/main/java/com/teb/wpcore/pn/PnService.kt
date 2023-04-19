package com.teb.wpcore.pn

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


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
    override fun onNewToken( token: String) {
//        Log.d(TAG, "Refreshed token: $token")

        Log.d("FCM", "Refreshed token : $token")


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }


}