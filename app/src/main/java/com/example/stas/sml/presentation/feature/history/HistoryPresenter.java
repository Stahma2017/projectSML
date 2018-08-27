package com.example.stas.sml.presentation.feature.history;

import com.example.stas.sml.data.database.AppDatabase;
import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HistoryPresenter {

    private WeakReference<HistoryContract.HistoryView> view;
    private final MapsModel interactor;
    private final CompositeDisposable compositeDisposable;
    private final LocationGateway locationGateway;
    private final ErrorHandler errorHandler;
    private final AppDatabase database;

    public HistoryPresenter(MapsModel interactor, CompositeDisposable compositeDisposable, LocationGateway locationGateway, ErrorHandler errorHandler, AppDatabase database) {
        this.interactor = interactor;
        this.compositeDisposable = compositeDisposable;
        this.locationGateway = locationGateway;
        this.errorHandler = errorHandler;
        this.database = database;
    }
    public void attachView(HistoryContract.HistoryView fragment) {
        view = new WeakReference<>(fragment);
    }
    public void detachView() {
        view = null;
        compositeDisposable.dispose();
    }

    public void saveVenue(long id,boolean saved){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                database.venueDao().updateSavedById(id, saved);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

      //  Disposable dis = database.venueDao().updateSavedById(id, saved)

    }

    public void getVenues(){
      Disposable dis = database.venueDao().getSaved(false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<VenueDb>>() {
                    @Override
                    public void accept(List<VenueDb> venueDbs) throws Exception {
                        view.get().showPlaces(venueDbs);
                    }
                });
      compositeDisposable.add(dis);
    }
}
