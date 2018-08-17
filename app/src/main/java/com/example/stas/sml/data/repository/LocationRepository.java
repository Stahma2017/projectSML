package com.example.stas.sml.data.repository;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.tbruyelle.rxpermissions2.RxPermissions;
import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import static android.content.Context.LOCATION_SERVICE;


public class LocationRepository implements LocationGateway {





    @Inject
    LocationRepository(){

    }

    @SuppressLint("MissingPermission")
    @Override
    public Single<Location> getCurrentLocation() {

        RxPermissions rxPermissions = null;
        return rxPermissions.request(Manifest.permission.CAMERA)
                .map(aBoolean -> {
                    if (aBoolean) {
                        LocationManager locationManager = (LocationManager) .getSystemService(LOCATION_SERVICE);
                        Criteria criteria = new Criteria();
                        String provider = locationManager.getBestProvider(criteria, true);
                        return locationManager.getLastKnownLocation(provider);
                    } else {
                        throw new Exception("Not Granted");
                    }
                }).singleOrError();
    }


}
