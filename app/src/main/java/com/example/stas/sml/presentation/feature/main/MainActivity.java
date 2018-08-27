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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.presentation.feature.history.HistoryFragment;
import com.example.stas.sml.presentation.feature.save.SaveFragment;
import com.example.stas.sml.presentation.feature.venueselected.VenueSelectedFragment;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;
import java.util.List;

import javax.inject.Inject;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    @Inject
    ActivityContract.Presenter presenter;
    @Inject
    ErrorHandler errorHandler;
    @Inject
    MapsFragment mapsFragment;
    @Inject
    VenuelistFragment venuelistFragment;
    @Inject
    VenueSelectedFragment venueSelectedFragment;
    @Inject
    HistoryFragment historyFragment;
    @Inject
    SaveFragment saveFragment;

    @BindView(R.id.bottomContainer)FrameLayout bottomContainer;
    @BindView(R.id.bottomAppBar)BottomNavigationView bottomNavigation;
    @BindView(R.id.fragment_container)FrameLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        App.getInstance().addMapsComponent().injectMainActivity(this);
        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.checkNetworkConnection(this);
        bottomNavigation.setSelectedItemId(R.id.action_map);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, mapsFragment);
        ft.commit();

        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_map:
                    menuItem.setChecked(true);
                    displayMapsFragment();
                    break;
                case R.id.action_account:
                    menuItem.setChecked(true);
                    displaySaveFragment();
                    break;
                case R.id.action_places:
                    menuItem.setChecked(true);
                    displayHistoryFragment();
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
        ft.show(mapsFragment);
        ft.remove(venuelistFragment);
        ft.remove(venueSelectedFragment);
        ft.remove(historyFragment);
        ft.remove(saveFragment);
        ft.commit();
    }

    public void displayHistoryFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(venuelistFragment);
        ft.remove(venueSelectedFragment);
        ft.remove(saveFragment);
        ft.add(R.id.fragment_container, historyFragment);
        ft.commit();
    }

    public void displaySaveFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(venuelistFragment);
        ft.remove(venueSelectedFragment);
        ft.remove(historyFragment);
        ft.add(R.id.fragment_container, saveFragment);
        ft.commit();

    }

    public void displayVenuelistFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, venuelistFragment);
        ft.hide(mapsFragment);
        ft.commit();
    }



    public void displayVenueSelectedFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, venueSelectedFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void pointLocationOnMap(double langtitude, double longtitude, String name){
        mapsFragment.pointLocation(langtitude, longtitude, name);
        displayMapsFragment();
        bottomNavigation.setSelectedItemId(R.id.action_map);
    }

    public void hideToolbar(){
        bottomContainer.setVisibility(View.GONE);
    }

    public void showToolbar(){
        bottomContainer.setVisibility(View.VISIBLE);
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