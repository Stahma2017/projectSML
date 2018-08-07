package com.example.stas.sml.presentation.feature.map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.View;

import com.example.stas.sml.CanShowError;
import com.example.stas.sml.data.model.venuedetailedmodel.Venue;
import com.example.stas.sml.domain.entity.VenueEntity;
import com.example.stas.sml.presentation.base.BaseView;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;


public interface MapsContract {
    interface MapView extends BaseView, CanShowError {
        /* void goToPictureActivity(List<String> urls);*/
       /* void showSlider(List<String> urls);*/
       public void showSuggestions();

        Location getCurrentLocation();
    }

    interface Presenter {
        void attachView(MapView view);

        void detachView();

        void checkNetworkConnection();

        void onBindCategoryRowViewAtPosition(int position, MapsContract.CategoryRowView rowView);
        int getCategoryRowCount();

        void onBindSuggestionRowViewAtPosition(int position, MapsContract.CategorySuggestionRowView rowView);
        int getSuggestionRowCount();
        void loadVenueList(int position);
    }

    interface Model {
        //  Single<String> loadVenueId(String latLng);
      //  Observable<VenueEntity> loadPhotos(String latLng);

        Observable<Boolean> observeConnectionStates();

      Observable<com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity> search(Location location, String categoryId);


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