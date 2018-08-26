package com.example.stas.sml.presentation.feature.venueselected;

import android.location.Location;

import com.example.stas.sml.data.database.AppDatabase;
import com.example.stas.sml.data.database.dao.VenueDao;
import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.utils.UrlHelper;

import java.lang.ref.WeakReference;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class VenueSelectedPresenter {

    private WeakReference<VenueSelectContract.VenueSelectView> view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;
    private final LocationGateway locationGateway;
    private final ErrorHandler errorHandler;
    private final AppDatabase database;

    public VenueSelectedPresenter(MapsModel interactor, CompositeDisposable compositeDisposable, LocationGateway locationGateway, ErrorHandler errorHandler, AppDatabase database) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.locationGateway = locationGateway;
        this.errorHandler = errorHandler;
        this.database = database;
    }

    public void attachView(VenueSelectContract.VenueSelectView fragment) {
        view = new WeakReference<>(fragment);
    }

    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }

    public void getLocationForVenueDetailed(String venueId){
        Disposable dis = locationGateway.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> view.get().deliverLocationForpreveious(location, venueId)
                        ,errorHandler::proceed);
        compositeDisposable.add(dis);
    }
    public void saveVenueToDb(VenueEntity venue){
        VenueDb venueDb = new VenueDb();
        venueDb.name = venue.getName();
        venueDb.address = venue.getLocation().getAddress();
        venueDb.latitude = venue.getLocation().getLat();
        venueDb.longitude = venue.getLocation().getLng();
        venueDb.imageUrl = UrlHelper.getUrlToPhoto(venue.getBestPhoto().getPrefix(), venue.getBestPhoto().getSuffix());
        venueDb.workStatus = venue.getHours().getStatus();
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                database.venueDao().insert(venueDb);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                view.get().showSuccess();
            }

            @Override
            public void onError(Throwable e) {
                errorHandler.proceed(e);
            }
        });
    }

    public void getVenuesWithCategory(String venueId,Location location){

        Disposable venueListDisposable = interactor.loadDetailedVenues(venueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venue -> view.get().showVenueSelected(venue, location)
                ,errorHandler::proceed);
        compositeDisposable.add(venueListDisposable);
    }

    public double distanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        float pk = (float) (180.f/Math.PI);
        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        return  6366000 * tt/1000;
    }

}
