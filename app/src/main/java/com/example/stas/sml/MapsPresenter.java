package com.example.stas.sml;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.gms.maps.model.LatLng;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter implements MapsContract.Presenter{

    private MapsContract.View mapsActivity;
    private MapsModel model = new MapsModel();

    @Override
    public Disposable checkNetworkConnection() {
        return ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectedToInternet -> mapsActivity.onNetworkConnectionChanged());
    }

    @Override
    public void attachView(MapsContract.View view) {
        mapsActivity = view;
    }


    @Override
    public void detachView(){
        mapsActivity = null;
    }

    @Override
    public void loadVenueId(LatLng latLng){
        String point = latLng.latitude + "," + latLng.longitude;
        model.loadVenueId(point, venueId -> mapsActivity.goToPictureActivity(venueId));
    }

}
