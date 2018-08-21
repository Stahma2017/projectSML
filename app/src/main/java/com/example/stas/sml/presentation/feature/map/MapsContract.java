package com.example.stas.sml.presentation.feature.map;

import android.location.Location;

import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.base.CanShowError;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MapsContract {

    interface MapsView extends CanShowError {

        void showPlacesByCategory(List<VenueEntity> venues);
        void showSearchSuggestions(List<Minivenue> minivenues);
        void showBottomSheet(Location location, LatLng latLng);
        void toCurrentLocation(Location location);
        void deliverLocationToCategories(Location location, String category);
    }






}
