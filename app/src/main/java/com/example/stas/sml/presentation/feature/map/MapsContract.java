package com.example.stas.sml.presentation.feature.map;

import android.location.Location;

import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;

import java.util.List;

public interface MapsContract {

    interface MapsView {

        Location getCurrentLocation();
        void showPlacesByCategory(VenueEntity venue);
        void showSearchSuggestions(List<Minivenue> minivenues);
        void showLocation(Location location);
    }






}
