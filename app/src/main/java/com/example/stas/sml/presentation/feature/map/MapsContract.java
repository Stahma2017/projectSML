package com.example.stas.sml.presentation.feature.map;

import com.example.stas.sml.CanShowError;
import com.example.stas.sml.domain.entity.VenueEntity;
import com.example.stas.sml.presentation.base.BaseView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;


public interface MapsContract {
    interface MapView extends BaseView, CanShowError {
        /* void goToPictureActivity(List<String> urls);*/
        //void showSlider(List<String> urls);
    }

    interface Presenter {
        void attachView(MapView view);

        void detachView();

        void checkNetworkConnection();

        void loadVenueId(LatLng latLng);
    }

    interface Model {

        Observable<VenueEntity> loadPhotos(String latLng);

        Observable<Boolean> observeConnectionStates();
    }
}
