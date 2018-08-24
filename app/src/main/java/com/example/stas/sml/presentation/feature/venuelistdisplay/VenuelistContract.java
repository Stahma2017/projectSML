package com.example.stas.sml.presentation.feature.venuelistdisplay;

import android.location.Location;

import com.example.stas.sml.Category;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.base.CanShowError;

import java.util.List;

public interface VenuelistContract {

    interface VenuelistView extends CanShowError {
        void deliverLocationForpreveious(Location location, String categoryId);
        void showPlacesByCategory(List<VenueEntity> venues);
        void showSearchSuggestions(List<Minivenue> minivenues);
        void deliverLocationToQuerySumbit(Location location,String query);
        void showPlacesBySubmit(List<VenueEntity> venue);
    }


}
