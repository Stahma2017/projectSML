package com.example.stas.sml;

import com.google.android.gms.maps.model.LatLng;
import java.lang.ref.WeakReference;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsPresenter implements MapsContract.Presenter {

    private WeakReference<MapsContract.View> mapsView;
    private MapsModel model = new MapsModel();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void checkNetworkConnection() {
        Disposable NetworkConnectionDisposable = model.observeConnectionStates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnected) throws Exception {
                        if (!isConnected){
                            mapsView.get().displayErrorDialog("internet connection lost");}
                        }
                });
        compositeDisposable.add(NetworkConnectionDisposable);
    }

    @Override
    public void attachView(MapsContract.View view) {
        mapsView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        mapsView = null;
        compositeDisposable.dispose();
    }

    @Override
    public void loadVenueId(LatLng latLng) {
        String point = latLng.latitude + "," + latLng.longitude;
       Disposable VenueIdRequestDisposable = model.loadVenueId(point)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(venueId -> mapsView.get().goToPictureActivity(venueId),
                       Throwable::printStackTrace);
     compositeDisposable.add(VenueIdRequestDisposable);
    }

    // TODO: 16.07.2018 handle possible null mapsView.get() object
    /*private View getView(){
        View view = getView();
        if(view != null) {
            return view;
        }
    }*/
}
