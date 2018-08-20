package com.example.stas.sml.data.repository;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.stas.sml.di.annotations.MapsFragmentScope;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Single;
import static android.content.Context.LOCATION_SERVICE;


public class LocationRepository implements LocationGateway {

    private Fragment fragment;

   @Inject
    public LocationRepository(Fragment fragment){
        this.fragment = fragment;
    }

    @SuppressLint("MissingPermission")
    @Override
    public Single<Location> getCurrentLocation() {

        RxPermissions rxPermissions = new RxPermissions(fragment);
        return rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .map(aBoolean -> {
                    if (aBoolean) {
                        LocationManager locationManager = (LocationManager)fragment.getContext().getSystemService(LOCATION_SERVICE);
                        Criteria criteria = new Criteria();
                        String provider = locationManager.getBestProvider(criteria, true);
                        return locationManager.getLastKnownLocation(provider);
                    } else {
                        throw new Exception("Not Granted");
                    }
                }).singleOrError();
    }

}
