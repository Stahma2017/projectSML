package com.example.stas.sml.presentation.feature.map.map;

import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.feature.map.querysubmit.VenuesByQuerySubmitFragment;

import io.reactivex.disposables.CompositeDisposable;

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
}
