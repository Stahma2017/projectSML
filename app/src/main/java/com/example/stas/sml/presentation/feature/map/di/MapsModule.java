package com.example.stas.sml.presentation.feature.map.di;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.di.annotations.MapsScope;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.map.MapsActivity;
import com.example.stas.sml.presentation.feature.map.MapsContract;
import com.example.stas.sml.presentation.feature.map.MapsPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class MapsModule {

   /* @MapsScope
    @NonNull
    @Provides
    MapsPresenter provideMapsPresenter(ErrorHandler errorHandler, CompositeDisposable compositeDisposable,
                                                MapsContract.Model mapsModel) {
        return new MapsPresenter(mapsModel, errorHandler, compositeDisposable);
    }

    @MapsScope
    @NonNull
    @Provides
    MapsContract.Model provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }

    @MapsScope
    @NonNull
    @Provides
    MapsActivity provideMapsActivity(MapsActivity mapsActivity) {
        return mapsActivity;
    }*/
}
