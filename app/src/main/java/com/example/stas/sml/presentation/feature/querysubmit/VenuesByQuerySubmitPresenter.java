package com.example.stas.sml.presentation.feature.querysubmit;

import com.example.stas.sml.presentation.feature.main.ActivityContract;

import io.reactivex.disposables.CompositeDisposable;


public class VenuesByQuerySubmitPresenter {

    private VenuesByQuerySubmitFragment view;

    private final ActivityContract.Model model;
    private final CompositeDisposable compositeDisposable;

    public VenuesByQuerySubmitPresenter(ActivityContract.Model model, CompositeDisposable compositeDisposable) {
        this.model = model;
        this.compositeDisposable = compositeDisposable;
    }

    public void attachView(VenuesByQuerySubmitFragment fragment) {
        view = fragment;

    }


    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }

    /*
    public void getVenuesByQuerySubmit(String querry){
        Location currentLocation = mapsView.get().getCurrentLocation();

        venues.clear();
        Disposable venueListDisposable = model.loadVenuesByQuerySubmition(currentLocation, querry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((VenueEntity venue) -> {
                            venues.add(venue);
                            mapsView.get().showPlacesByQuerySubmit(venues);
                        },
                        errorHandler::proceed);
        compositeDisposable.add(venueListDisposable);
    }*/


}
