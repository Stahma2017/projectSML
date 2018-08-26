package com.example.stas.sml.presentation.feature.history;

import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.presentation.base.CanShowError;

import java.util.List;

public interface HistoryContract {

    interface HistoryView extends CanShowError{
       void showPlaces(List<VenueDb> venueDbs);

    }
}
