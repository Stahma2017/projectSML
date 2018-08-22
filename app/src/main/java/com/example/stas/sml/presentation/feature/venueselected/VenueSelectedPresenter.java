package com.example.stas.sml.presentation.feature.venueselected;

import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class VenueSelectedPresenter {

    private WeakReference<VenueSelectContract.VenueSelectView> view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;
    private final LocationGateway locationGateway;
    private final ErrorHandler errorHandler;

    public VenueSelectedPresenter(MapsModel interactor, CompositeDisposable compositeDisposable, LocationGateway locationGateway, ErrorHandler errorHandler) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.locationGateway = locationGateway;
        this.errorHandler = errorHandler;
    }

    public void attachView(VenueSelectContract.VenueSelectView fragment) {
        view = new WeakReference<>(fragment);
    }

    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }
}
