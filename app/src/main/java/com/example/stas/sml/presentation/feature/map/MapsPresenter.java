package com.example.stas.sml.presentation.feature.map;

import android.location.Location;

import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.domain.interactor.MapsModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter  {

    private MapsFragment view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;

    public MapsPresenter(MapsModel interactor, CompositeDisposable compositeDisposable) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
    }
    public void attachView(MapsFragment fragment) {
        view = fragment;
    }

    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }

    public void getVenuesWithCategory(String categoryId){
        Location currentLocation = view.getCurrentLocation();

        Disposable venueListDisposable = interactor.loadVenuesWithCategory(currentLocation, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((VenueEntity venue) -> {
                    view.showPlacesByCategory(venue);
                }
//                ,errorHandler::proceed
                );
        compositeDisposable.add(venueListDisposable);
    }

    public void getTextSuggestions(String querry){

        Disposable bla = interactor.loadTextSuggestions(querry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(minivenues ->
                        view.showSearchSuggestions(minivenues)
                );
        compositeDisposable.add(bla);
    }

}
