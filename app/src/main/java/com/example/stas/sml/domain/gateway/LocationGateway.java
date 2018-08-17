package com.example.stas.sml.domain.gateway;

import android.location.Location;

import io.reactivex.Single;

public interface LocationGateway {
    Single<Location> getCurrentLocation();
}
