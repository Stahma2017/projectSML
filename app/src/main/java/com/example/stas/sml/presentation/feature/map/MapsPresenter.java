package com.example.stas.sml.presentation.feature.map;
import android.location.Location;

import com.example.stas.sml.Category;


import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.base.ErrorHandler;

import com.example.stas.sml.utils.CategoryList;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter implements MapsContract.Presenter {

    private WeakReference<MapsContract.MapView> mapsView;
    private final MapsContract.Model model;
    private final ErrorHandler errorHandler;
    private final CompositeDisposable compositeDisposable;

    private List<VenueEntity> venues = new ArrayList<>();


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
    public void getVenuesWithCategory(int position){
        String categoryId = CategoryList.getInstance().getCategoryList().get(position).getCategoryId();
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
        compositeDisposable.add(venueListDisposable);
    }

    @Override
    public void getTextSuggestions(String querry){

        Disposable bla = model.loadTextSuggestions(querry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(minivenues ->
                    mapsView.get().showSearchSuggestions(minivenues)
                );
        compositeDisposable.add(bla);
    }

    @Override
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
    }


    @Override
    public void onBindCategoryRowViewAtPosition(int position, MapsContract.CategoryRowView rowView){
        Category category = CategoryList.getInstance().getCategoryList().get(position);
        rowView.setIcon(category.getCategoryImage());
        rowView.setName(category.getCategoryName());
    }

    @Override
    public void onBindSuggestionRowViewAtPosition(int position, MapsContract.CategorySuggestionRowView rowView) {
        VenueEntity venue = venues.get(position);
        rowView.setName(venue.getName());
        rowView.setAddress(venue.getLocation().getAddress());
        rowView.setDescription(venue.getDescription());
        rowView.setWorkStatus(venue.getHours().getStatus());
        rowView.setDistance(String.format(Locale.US,"%.1f км", ((double)venue.getDistance())/1000));
        rowView.setRating((float)(venue.getRating()/2));
        rowView.setLogo(venue.getPage().getPageInfo().getBanner());
//        rowView.setWorkIndicator(venue.getHours().getOpen());
    }

    @Override
    public int getSuggestionRowCount() {
        return venues.size();
    }

    public int getCategoryRowCount(){
        return CategoryList.getInstance().getCategoryList().size();
    }




}