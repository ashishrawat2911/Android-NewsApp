package dev.ashish.newsapp.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dev.ashish.newsapp.network.NetworkConnection;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver  {
    ConnectivityListener connectivityListener;

    public ConnectivityBroadcastReceiver(ConnectivityListener connectivityListener) {
        this.connectivityListener = connectivityListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkConnection.isConnected(context) && connectivityListener != null) {
            connectivityListener.onNetworkConnected();
        }

    }

    public interface ConnectivityListener {
        void onNetworkConnected();
    }
}
