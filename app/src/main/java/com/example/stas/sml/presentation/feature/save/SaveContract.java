package com.example.stas.sml.presentation.feature.save;

import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.presentation.base.CanShowError;

import java.util.List;

public interface SaveContract {

    interface SaveView extends CanShowError {
        void showPlaces(List<VenueDb> venueDbs);

    }
}
