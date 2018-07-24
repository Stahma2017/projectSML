package com.example.stas.sml.presentation.feature.map;
import com.example.stas.sml.presentation.base.ErrorHandler;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter implements MapsContract.Presenter {

    private WeakReference<MapsContract.MapView> mapsView;
    private final MapsContract.Model model;
    private final ErrorHandler errorHandler;
    private final CompositeDisposable compositeDisposable;

    public MapsPresenter(MapsContract.Model model,
                         ErrorHandler errorHandler,
                         CompositeDisposable compositeDisposable) {
        this.model = model;
        this.errorHandler = errorHandler;
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void checkNetworkConnection() {
        Disposable networkConnectionDisposable = model.observeConnectionStates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {
                    if (!isConnected) {
                        mapsView.get().showError("internet connection lost");
                    }
                });
        compositeDisposable.add(networkConnectionDisposable);
    }

    @Override
    public void attachView(MapsContract.MapView view) {
        mapsView = new WeakReference<>(view);
        errorHandler.attachView(view);
    }

    @Override
    public void detachView() {
        mapsView = null;
        errorHandler.detachView();
        compositeDisposable.dispose();
    }

    @Override
    public void loadVenueId(LatLng latLng) {
        String point = latLng.latitude + "," + latLng.longitude;
        Disposable venueIdRequestDisposable = model.loadPhotos(point)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venueEntity -> mapsView.get().showSlider(venueEntity.getPhotosUrls()),
                        errorHandler::proceed);
        compositeDisposable.add(venueIdRequestDisposable);
    }


}
