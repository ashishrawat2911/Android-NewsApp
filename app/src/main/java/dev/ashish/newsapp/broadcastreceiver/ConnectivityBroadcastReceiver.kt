package dev.ashish.newsapp.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dev.ashish.newsapp.network.NetworkConnection

class ConnectivityBroadcastReceiver(var connectivityListener: ConnectivityListener?) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (NetworkConnection.isConnected(context) && connectivityListener != null) {
            connectivityListener!!.onNetworkConnected()
        }
    }

    interface ConnectivityListener {
        fun onNetworkConnected()
    }

}