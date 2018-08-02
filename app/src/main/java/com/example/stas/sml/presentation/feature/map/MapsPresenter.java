package com.example.stas.sml.presentation.feature.map;
import com.example.stas.sml.Category;
import com.example.stas.sml.R;
import com.example.stas.sml.presentation.base.ErrorHandler;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter implements MapsContract.Presenter {

    private WeakReference<MapsContract.MapView> mapsView;
    private final MapsContract.Model model;
    private final ErrorHandler errorHandler;
    private final CompositeDisposable compositeDisposable;
    private List<Category> categories;




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
       /* String point = latLng.latitude + "," + latLng.longitude;
        Disposable venueIdRequestDisposable = model.loadPhotos(point)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(venueEntity -> mapsView.get().showSlider(venueEntity.getPhotosUrls()),
                        errorHandler::proceed);
        compositeDisposable.add(venueIdRequestDisposable);*/
    }

    public void onBindCategoryRowViewAtPosition(int position, MapsContract.CategoryRowView rowView){
        Category category = categories.get(position);
        rowView.setIcon(category.getCategoryImage());
        rowView.setName(category.getCategoryName());
    }

    public int getCategoryRowCount(){
        return categories.size();
    }



    public void populateCategories(){
        categories = new ArrayList<>();
        categories.add(new Category(R.drawable.ic_category_travel, "Путешествия и транспорт"));
        categories.add(new Category(R.drawable.ic_category_education, "Высшие учебные заведения"));
        categories.add(new Category(R.drawable.ic_category_entertainment, "Искусство и развлечения"));
        categories.add(new Category(R.drawable.ic_category_education, "Высшие учебные заведения"));
        categories.add(new Category(R.drawable.ic_category_food, "Кафе и рестораны"));
        categories.add(new Category(R.drawable.ic_category_nightlife, "Ночные заведения"));
        categories.add(new Category(R.drawable.ic_category_parks_outdoors, "Заведения на свежем воздухе"));
        categories.add(new Category(R.drawable.ic_category_building, "Услуги и учреждения"));
        categories.add(new Category(R.drawable.ic_category_shops, "Магазины и услуги"));
    }

    @Override
    public void onItemClickedAtPosition(int position){
        if (position == 1){
            mapsView.get().showSuggestions();
        }






    }


}