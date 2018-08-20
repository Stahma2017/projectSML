package com.example.stas.sml.presentation.feature.venuelistdisplay;

import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.feature.map.MapsContract;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class VenuelistPresenter {


    private WeakReference<VenuelistContract.VenuelistView> view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;
    private final LocationGateway locationGateway;

    public VenuelistPresenter(MapsModel interactor, CompositeDisposable compositeDisposable, LocationGateway locationGateway) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.locationGateway = locationGateway;
    }

    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }

    public void attachView(VenuelistContract.VenuelistView fragment) {
        view = new WeakReference<>(fragment);
    }
}
