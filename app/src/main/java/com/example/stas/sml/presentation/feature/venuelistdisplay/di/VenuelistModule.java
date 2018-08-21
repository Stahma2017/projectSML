package com.example.stas.sml.presentation.feature.venuelistdisplay.di;

import android.support.v4.app.Fragment;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.di.annotations.VenuelistFragmentScope;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class VenuelistModule {

    private Fragment venulistFragment;

    public VenuelistModule(VenuelistFragment venulistFragment){
        this.venulistFragment = venulistFragment;
    }

    @VenuelistFragmentScope
    @NonNull
    @Provides
    LocationGateway provideLocationGateway(){
        return new LocationRepository(venulistFragment);
    }

    @VenuelistFragmentScope
    @NonNull
    @Provides
    VenuelistPresenter provideMapsPresenter(CompositeDisposable compositeDisposable,
                                            MapsModel mapsModel, LocationGateway locationGateway, ErrorHandler errorHandler) {
        return new VenuelistPresenter(mapsModel, compositeDisposable, locationGateway, errorHandler);
    }

    @VenuelistFragmentScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }

}
