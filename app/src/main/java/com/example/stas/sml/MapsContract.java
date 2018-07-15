package com.example.stas.sml;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.disposables.Disposable;

public interface MapsContract {
    interface View{
        void onNetworkConnectionChanged();
        void goToPictureActivity(String venueId);
    }
    interface Presenter{
        void attachView(View view);
        void detachView();
        Disposable checkNetworkConnection();
        void loadVenueId(LatLng latLng);

    }
    interface Model{
        void loadVenueId(String latLng, MapsModel.LoadVenueIdCallback callback);
    }
}
