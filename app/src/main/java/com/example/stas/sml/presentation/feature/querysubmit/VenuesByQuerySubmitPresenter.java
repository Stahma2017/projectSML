package com.example.stas.sml.presentation.feature.querysubmit;

import com.example.stas.sml.presentation.feature.main.MapsContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class VenuesByQuerySubmitPresenter {

    private VenuesByQuerySubmitFragment view;

    private final MapsContract.Model model;
    private final CompositeDisposable compositeDisposable;

    public VenuesByQuerySubmitPresenter(MapsContract.Model model, CompositeDisposable compositeDisposable) {
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

    public void getSmth(){
        Disposable bla = model.loadTextSuggestions("Магнит")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(minivenues ->
                        view.showToast(minivenues)
                );
        compositeDisposable.add(bla);
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
