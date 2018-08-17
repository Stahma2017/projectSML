package com.example.stas.sml.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.stas.sml.R;

public class LocationService extends Service {

    private static final String TAG = "SERVICE";
    private static final int NOTIFICATION_ID = 777;
    private LocationManager locationManager = null;
    private static final int LOCATION_INTERVAL = 0;
    private static final float LOCATION_DISTANCE = 0;

    private class Listener implements LocationListener {
        Location lastLocation;

        Listener(String provider) {
            lastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            lastLocation.set(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    Listener locationListener = new Listener(LocationManager.GPS_PROVIDER);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        initializeLocationManager();
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);
        } catch (java.lang.SecurityException ex){
            Log.d(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex){
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }



        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.distance)
                .setContentTitle("My Awesome App")
                .setContentText("Doing some work...")
                .build();

        startForeground(NOTIFICATION_ID, notification);
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        if (locationManager != null){
            try{
                locationManager.removeUpdates(locationListener);
            }catch (Exception ex){
                Log.i(TAG, "fail to remove location listners, ignore", ex);
            }
        }
    }

        private void initializeLocationManager() {
        Log.d(TAG, "initializeLocationManager");
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }



}
