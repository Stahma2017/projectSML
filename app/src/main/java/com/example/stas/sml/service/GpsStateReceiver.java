package com.example.stas.sml.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GpsStateReceiver extends BroadcastReceiver {

    // TODO: 16.07.2018 to loadVenuesWithCategory about rxJava implementation of this feature
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "GPS on/off", Toast.LENGTH_SHORT).show();
    }
}
