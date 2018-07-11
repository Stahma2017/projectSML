package com.example.stas.sml;

import android.Manifest;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.stas.sml.Model.PlaceResponce;
import com.example.stas.sml.VenueDetailedModel.VenueDetailed;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;


@RuntimePermissions
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    Marker marker;
    String venueId;

    private Api serverApi = RetrofitClient.getInstance().getApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMap();
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
        Log.d("Markers", "Click on " + marker.getPosition());
    }


    @Override
    public void onMapLongClick(LatLng latLng) {

        setMarker(latLng);
        MapsActivityPermissionsDispatcher.setMarkerWithPermissionCheck(this, latLng);


        //когда с search(latLng) Ошибка в логах: ll must be of the form XX.XX,YY.YY
        Call<PlaceResponce> searches = serverApi.search("44.3,37.2",
                10000.0);
        searches.enqueue(new Callback<PlaceResponce>() {
            @Override
            public void onResponse(Call<PlaceResponce> call, Response<PlaceResponce> response) {
                if (response.isSuccessful()) {
                    venueId = response.body().getResponse().getVenues().get(0).getId();
                }
                else{
                    Log.d("id", "Unsuccessful response");
                }
            }
            @Override
            public void onFailure(Call<PlaceResponce> call, Throwable t) {
                Log.d("No", "Internet");
                if(t instanceof IOException){
                    t.getStackTrace();
                }
            }
        });

        //Ошибка в логах: Value VENUE_ID is invalid for venue id
        Call<VenueDetailed> venuePhotos = serverApi.getVenue("58f5c78f419a9e48f46e0b50");

        venuePhotos.enqueue(new Callback<VenueDetailed>() {
            @Override
            public void onResponse(Call<VenueDetailed> call, Response<VenueDetailed> response) {
                if (response.isSuccessful()) {
                    String url;
                    url = response.body().getResponse().getVenue().getPage().getUser().getPhoto().getPrefix() +
                            response.body().getResponse().getVenue().getPage().getUser().getPhoto().getSuffix();
                    Log.d("FourSquare: ", url);


                    Intent intent = new Intent(MapsActivity.this, PictureReceiverActivity.class);
                    intent.putExtra(PictureReceiverActivity.KEY_PICTURE_URL, url);
                    startActivity(intent);
                }
                else{
                    Log.d("No: ", "fail");
                }
            }

            @Override
            public void onFailure(Call<VenueDetailed> call, Throwable t) {
                Log.d("No", "Internet");
                if(t instanceof IOException){
                    t.getStackTrace();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void setUpMap(){
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForMap(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

}