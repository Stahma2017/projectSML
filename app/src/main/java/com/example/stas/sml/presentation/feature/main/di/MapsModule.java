package com.example.stas.sml.presentation.feature.main.di;

import android.support.v7.app.AppCompatActivity;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.di.annotations.MapsScope;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.main.ActivityContract;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.main.MainActivityPresenter;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;
import com.example.stas.sml.presentation.feature.venueselected.VenueSelectedFragment;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class MapsModule {

    @MapsScope
    @NonNull
    @Provides
    ActivityContract.Presenter provideMapsPresenter(ErrorHandler errorHandler, CompositeDisposable compositeDisposable,
                                                    ActivityContract.Model mapsModel) {
        return new MainActivityPresenter(mapsModel, errorHandler, compositeDisposable);
    }

    @MapsScope
    @NonNull
    @Provides
    ActivityContract.Model provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }

    @MapsScope
    @NonNull
    @Provides
    MainActivity provideMapsActivity(MainActivity mainActivity) {
        return mainActivity;
    }




    @NonNull
    @Provides
    MapsFragment provideMapsFragment(){
        return new MapsFragment();
    }

    @NonNull
    @Provides
    VenuelistFragment provideVenulistFragment(){
        return new VenuelistFragment();
    }

    @NonNull
    @Provides
    VenueSelectedFragment provideVenueSelectedFragment(){
        return new VenueSelectedFragment();
    }



}
