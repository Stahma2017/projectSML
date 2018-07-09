package com.example.stas.sml;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private MarkerOptions markerOptions;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (marker != null){
            marker.remove();
        }
        markerOptions = new MarkerOptions().position(latLng).title("Custom location");
        marker = mMap.addMarker(markerOptions);
        Log.d("Markers", "Click on " + marker.getPosition());
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (marker != null){
            marker.remove();
        }
        markerOptions = new MarkerOptions().position(latLng).title("Custom location");
        marker = mMap.addMarker(markerOptions);
        Log.d("Markers", "Long click on " + marker.getPosition());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

    }
}
