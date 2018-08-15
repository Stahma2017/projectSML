package com.example.stas.sml.presentation.feature.querysubmit.di;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.di.annotations.QueryVenuesScope;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.feature.querysubmit.VenuesByQuerySubmitFragment;
import com.example.stas.sml.presentation.feature.querysubmit.VenuesByQuerySubmitPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class QueryVenuesModule {

    @QueryVenuesScope
    @NonNull
    @Provides
    VenuesByQuerySubmitPresenter provideVenuesByQuerySubmitPresenter(MapsModel mapsModel, CompositeDisposable compositeDisposable){
        return new VenuesByQuerySubmitPresenter(mapsModel, compositeDisposable);
    }

    @QueryVenuesScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }

    @QueryVenuesScope
    @NonNull
    @Provides
    VenuesByQuerySubmitFragment provideVenuesByQuerySubmitFragment(VenuesByQuerySubmitFragment fragment){
        return fragment;
    }


}
