package com.example.stas.sml.data.repository;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.List;
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
        return rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .map(aBoolean -> {
                    if (aBoolean) {
                        Location location;
                        LocationManager locationManager = (LocationManager)fragment.getContext().getSystemService(LOCATION_SERVICE);
                        List<String> providers = locationManager.getAllProviders();
                        for (String provider: providers) {
                                location = locationManager.getLastKnownLocation(provider);
                            if (location != null){
                                    return location;
                            }
                        }
                        throw new Exception("Providers unvailable");
                    }
                    else {
                        throw new Exception("Not Granted");
                    }
                }).singleOrError();
    }

}
