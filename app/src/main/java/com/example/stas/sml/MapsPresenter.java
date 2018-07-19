package com.example.stas.sml;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter implements MapsContract.Presenter {

    private WeakReference<MapsContract.MapView> mapsView;
    private MapsModel model = new MapsModel();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ErrorHandler errorHandler;

    public MapsPresenter(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public void checkNetworkConnection() {
        Disposable networkConnectionDisposable = model.observeConnectionStates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnected) throws Exception {
                        if (!isConnected) {
                            mapsView.get().showError("internet connection lost");
                        }
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
        Disposable venueIdRequestDisposable = model.loadVenueId(point)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venueId -> mapsView.get().goToPictureActivity(venueId),
                        throwable -> errorHandler.proceed(throwable));
        compositeDisposable.add(venueIdRequestDisposable);
    }

    public void getSlider(String venueId){
        model.loadPhotos(venueId);


    }

}
