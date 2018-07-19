package com.example.stas.sml;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.stas.sml.bottomSheet.GoogleMapsBottomSheetBehavior;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;

@RuntimePermissions
public class MapsActivity extends AppCompatActivity implements MapsContract.MapView, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    private Marker marker;
    private MapsPresenter presenter;

    //BottomSheet
    private GoogleMapsBottomSheetBehavior behavior;
    private SliderLayout parallax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_maps);
        ErrorHandler errorHandler = new BaseErrorHandler(this);
        presenter = new MapsPresenter(errorHandler);
        presenter.attachView(this);
        setUpMap();
        presenter.checkNetworkConnection();

        final View bottomsheet = findViewById(R.id.bottomsheet);
        behavior = GoogleMapsBottomSheetBehavior.from(bottomsheet);
        parallax = (SliderLayout)findViewById(R.id.parallax);
        behavior.setParallax(parallax);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("cat",R.drawable.cat);
        file_maps.put("dog",R.drawable.dog);
        file_maps.put("lemur",R.drawable.lemur);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            parallax.addSlider(textSliderView);
        }

        bottomsheet.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // set the height of the parallax to fill the gap between the anchor and the top of the screen
                CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(parallax.getMeasuredWidth(), behavior.getAnchorOffset());
                parallax.setLayoutParams(layoutParams);
                bottomsheet.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        behavior.setBottomSheetCallback(new GoogleMapsBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @GoogleMapsBottomSheetBehavior.State int newState) {


            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


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
        behavior.setHideable(true);
        behavior.setState(GoogleMapsBottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        setMarker(latLng);
        MapsActivityPermissionsDispatcher.setMarkerWithPermissionCheck(this, latLng);
      //  presenter.loadVenueId(latLng);
        behavior.setState(GoogleMapsBottomSheetBehavior.STATE_COLLAPSED);
        behavior.setHideable(false);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void goToPictureActivity(String venueId) {
        presenter.getSlider(venueId);

        Intent intent = new Intent(MapsActivity.this, PictureActivity.class);
        intent.putExtra(PictureActivity.VENUE_ID, venueId);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        behavior.anchorMap(mMap);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
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


    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForMap(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
