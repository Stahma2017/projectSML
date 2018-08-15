package com.example.stas.sml.presentation.feature.map;

import android.location.Location;

import com.example.stas.sml.CanShowError;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.presentation.base.BaseView;

import java.util.List;

import io.reactivex.Observable;


public interface MapsContract {
    interface MapView extends BaseView, CanShowError {

        /* void goToPictureActivity(List<String> urls);*/

       // void showPlacesByQuerySubmit(List<com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity> venues);
    }

    interface Presenter {
        void attachView(MapView view);

        void detachView();

        void checkNetworkConnection();

//        void onBindCategoryRowViewAtPosition(int position, MapsContract.CategoryRowView rowView);
//        int getCategoryRowCount();
//
//        void onBindSuggestionRowViewAtPosition(int position, MapsContract.CategorySuggestionRowView rowView);
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


    }
    interface CategoryRowView{
        void setIcon(int icon);
        void setName(String name);
    }

    interface CategorySuggestionRowView{
        void setWorkIndicator(boolean flag);
        void setLogo(String url);
        void setName(String name);
        void setDescription(String description);
        void setAddress(String address);
        void setWorkStatus(String workStatus);
        void setDistance(String distance);
        void setRating(float rating);
    }
}