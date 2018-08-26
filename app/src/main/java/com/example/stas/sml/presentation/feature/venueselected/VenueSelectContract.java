package com.example.stas.sml.presentation.feature.venueselected;

import android.location.Location;

import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.base.CanShowError;

public interface VenueSelectContract {

    interface VenueSelectView extends CanShowError{
        void deliverLocationForpreveious(Location location, String venueId);
        void showVenueSelected(VenueEntity venue, Location location);
        void showSuccess();

    }
}
