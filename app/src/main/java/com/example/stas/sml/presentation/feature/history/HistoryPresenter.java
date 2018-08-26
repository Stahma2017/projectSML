package com.example.stas.sml.presentation.feature.history;

import com.example.stas.sml.data.database.AppDatabase;
import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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

    public void getVenues(){
      Disposable dis = database.venueDao().getAll()
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
