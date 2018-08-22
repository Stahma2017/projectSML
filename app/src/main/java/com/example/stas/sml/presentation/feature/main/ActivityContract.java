package com.example.stas.sml.presentation.feature.main;

import android.content.Context;
import android.location.Location;

import com.example.stas.sml.presentation.base.CanShowError;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.presentation.base.BaseView;

import java.util.List;

import io.reactivex.Observable;


public interface ActivityContract {
    interface ActivityView extends BaseView, CanShowError {

        void displayMapsFragment();


       // void showPlacesByQuerySubmit(List<com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity> venues);
    }

    interface Presenter {
        void attachView(ActivityView view);

        void detachView();

        void checkNetworkConnection(Context context);

//        void onBindCategoryRowViewAtPosition(int position, ActivityContract.CategoryRowView rowView);
//        int getCategoryRowCount();
//
//        void onBindSuggestionRowViewAtPosition(int position, ActivityContract.CategorySuggestionRowView rowView);
//        int getSuggestionRowCount();
//        void getVenuesWithCategory(int position);
//
//        void getTextSuggestions(String querry);
//        void getVenuesByQuerySubmit(String querry);
    }

    interface Model {

        Observable<List<Minivenue>> loadTextSuggestions(String querry);

        Observable<Boolean> observeConnectionStates();

        Observable<com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity> loadVenuesWithCategory(Location location, String categoryId);

        Observable<com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity> loadVenuesByQuerySubmition(Location location, String query);

        Observable<com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity> loadDetailedVenues(String venueId);


    }
}