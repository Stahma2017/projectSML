package com.example.stas.sml.presentation.feature.map;
import com.example.stas.sml.MapsFragment;
import com.example.stas.sml.domain.entity.VenueEntity;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter {

    private WeakReference<MapsFragment> mapsView;
    private MapsModel model = new MapsModel();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ErrorHandler errorHandler;

    public MapsPresenter(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

   /* private final MapsContract.Model model;
 //   private final ErrorHandler errorHandler;
    private final CompositeDisposable compositeDisposable;*/

   /* public MapsPresenter(MapsContract.Model model, ErrorHandler errorHandler
                         CompositeDisposable compositeDisposable) {
        this.model = model;
        this.errorHandler = errorHandler;
         this.compositeDisposable = compositeDisposable;
    }
*/


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


    public void attachView(MapsFragment view) {
        mapsView = new WeakReference<>(view);
       errorHandler.attachView(view);
    }

    public void detachView() {
        mapsView = null;
        errorHandler.detachView();
        compositeDisposable.dispose();
    }

    public void loadVenueId(LatLng latLng) {
        String point = latLng.latitude + "," + latLng.longitude;
        Disposable venueIdRequestDisposable = model.loadPhotos(point)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VenueEntity>() {
                               @Override
                               public void accept(VenueEntity venueEntity) throws Exception {
                                   mapsView.get().showSlider(venueEntity.getPhotosUrls());
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable error) throws Exception {
                                errorHandler.proceed(error);
                            }
                        });
        compositeDisposable.add(venueIdRequestDisposable);
    }
}
