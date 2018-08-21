package com.example.stas.sml.presentation.feature.venuelistdisplay;

import android.location.Location;

import com.example.stas.sml.Category;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.base.CanShowError;

import java.util.List;

public interface VenuelistContract {

    interface VenuelistView extends CanShowError {

        void showPreviousCategories(Location location);
        void deliverLocationForpreveious(Location location, String categoryId);
        void showPlacesByCategory(List<VenueEntity> venues);


    }


}
