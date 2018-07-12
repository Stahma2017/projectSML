package com.example.stas.sml;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class NetworkManager {

    private Context context;
    private InternetConnectionListener listener;

    public interface InternetConnectionListener {
        void onConnectionLost();
    }

    public NetworkManager(Context context, InternetConnectionListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void listenInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
            @Override
            public void onLost(Network network) {
                super.onLost(network);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
            }
        });
    }
}
