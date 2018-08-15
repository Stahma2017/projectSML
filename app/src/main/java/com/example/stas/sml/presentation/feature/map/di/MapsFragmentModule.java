package com.example.stas.sml.presentation.feature.map.di;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.di.annotations.MapsFragmentScope;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.map.MapsPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class MapsFragmentModule {


    @MapsFragmentScope
    @NonNull
    @Provides
    MapsPresenter provideMapsPresenter(CompositeDisposable compositeDisposable,
                                       MapsModel mapsModel) {
        return new MapsPresenter(mapsModel, compositeDisposable);
    }

    @MapsFragmentScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }

    @MapsFragmentScope
    @NonNull
    @Provides
    MapsFragment provideMapsActivity(MapsFragment mapsFragment) {
        return mapsFragment;
    }


}
