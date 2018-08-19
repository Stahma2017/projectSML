package com.example.stas.sml.presentation.feature.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import javax.inject.Inject;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    @Inject
    ActivityContract.Presenter presenter;
    @Inject
    ErrorHandler errorHandler;

    @BindView(R.id.bottomAppBar)BottomNavigationView bottomNavigation;
    @BindView(R.id.fragment_container)FrameLayout fragmentContainer;

   // private Animation mFadeInAnimation, mFadeOutAnimation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        App.getInstance().addMapsComponent().injectMainActivity(this);
        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.checkNetworkConnection(this);
        displayMapsFragment(); // handle bottom navigation items states
        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_map:
                    displayMapsFragment();
                    break;
                case R.id.action_account:
                   /*locationRepository.getCurrentLocation()
                           .subscribe(new Consumer<Location>() {
                               @Override
                               public void accept(Location location) throws Exception {
                                   str = location.toString();
                               }
                           });*/

                    break;
            }
            return false;
        });

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
    public void displayMapsFragment(){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new MapsFragment());
        ft.commit();
    }

    public void displayVenuelistFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new VenuelistFragment());
        ft.commit();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForMap() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForMap() {
      /*  Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);*/
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void showPlacesByQuerySubmit(List<VenueEntity> venues){
     //   suggestionsBySubmitRecycler.setVisibility(View.VISIBLE);
     //   suggestionRecycler.setVisibility(View.GONE);
     //   placesBySubmitAdapter.setVenues(venues);
     //   placesBySubmitAdapter.notifyDataSetChanged();
    }



}