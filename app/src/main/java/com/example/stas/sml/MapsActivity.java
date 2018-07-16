package com.example.stas.sml;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@RuntimePermissions
public class MapsActivity extends FragmentActivity implements MapsContract.View, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Marker marker;
    private MapsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
        presenter.checkNetworkConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        setMarker(latLng);
        MapsActivityPermissionsDispatcher.setMarkerWithPermissionCheck(this, latLng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        setMarker(latLng);
        MapsActivityPermissionsDispatcher.setMarkerWithPermissionCheck(this, latLng);
        presenter.loadVenueId(latLng);
    }

    @Override
    public void goToPictureActivity(String venueId) {
        Intent intent = new Intent(MapsActivity.this, PictureActivity.class);
        intent.putExtra(PictureActivity.VENUE_ID, venueId);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    private void init(){
        presenter = new MapsPresenter();
        presenter.attachView(this);
        setUpMap();
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void setUpMap(){
        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void setMarker(LatLng latLng){
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Custom location");
        marker = mMap.addMarker(markerOptions);
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForMap(){
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayErrorDialog(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForMap(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
    }
}
