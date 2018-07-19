package com.example.stas.sml;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;
import io.reactivex.Single;


public interface MapsContract {
    interface MapView extends BaseView, CanShowError {
        void goToPictureActivity(String venueId);
    }

    interface Presenter {
        void attachView(MapView view);

        void detachView();

        void checkNetworkConnection();

        void loadVenueId(LatLng latLng);
    }

    interface Model {
        Single<String> loadVenueId(String latLng);

        Observable<Boolean> observeConnectionStates();
    }
}
