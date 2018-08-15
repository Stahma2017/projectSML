package com.example.stas.sml.presentation.feature.map;

import com.example.stas.sml.presentation.base.ErrorHandler;
import java.lang.ref.WeakReference;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements MapsContract.Presenter {

    private WeakReference<MapsContract.MapView> mapsView;

    private final MapsContract.Model model;
    private final ErrorHandler errorHandler;
    private final CompositeDisposable compositeDisposable;

    public MainActivityPresenter(MapsContract.Model model,
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

    public void getVenuesWithCategory(int position){
       /* String categoryId = CategoryList.getInstance().getCategoryList().get(position).getCategoryId();
        Location currentLocation = mapsView.get().getCurrentLocation();
        venues.clear();
        Disposable venueListDisposable = model.loadVenuesWithCategory(currentLocation, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((VenueEntity venue) -> {
                    venues.add(venue);
                    mapsView.get().showPlacesByCategory();
                },
                        errorHandler::proceed);
        compositeDisposable.add(venueListDisposable);*/
    }


    public void getTextSuggestions(String querry){

//        Disposable bla = model.loadTextSuggestions(querry)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(minivenues ->
//                    mapsView.get().showSearchSuggestions(minivenues)
//                );
//        compositeDisposable.add(bla);
    }


    public void getVenuesByQuerySubmit(String querry){
      /*  Location currentLocation = mapsView.get().getCurrentLocation();

        venues.clear();
        Disposable venueListDisposable = model.loadVenuesByQuerySubmition(currentLocation, querry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((VenueEntity venue) -> {
                            venues.add(venue);
                            mapsView.get().showPlacesByQuerySubmit(venues);
                        },
                        errorHandler::proceed);
        compositeDisposable.add(venueListDisposable);*/
    }
}