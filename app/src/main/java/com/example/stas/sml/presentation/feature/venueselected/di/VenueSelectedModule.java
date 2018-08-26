package com.example.stas.sml.presentation.feature.venueselected.di;

import android.support.v4.app.Fragment;

import com.example.stas.sml.data.database.AppDatabase;
import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.di.annotations.VenueSelectScope;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.venueselected.VenueSelectedFragment;
import com.example.stas.sml.presentation.feature.venueselected.VenueSelectedPresenter;
import com.example.stas.sml.presentation.feature.venueselected.adapter.GalleryRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class VenueSelectedModule {

    private Fragment venueSelectedFragment;

    public VenueSelectedModule(VenueSelectedFragment venueSelectedFragment){
        this.venueSelectedFragment = venueSelectedFragment;
    }

    @VenueSelectScope
    @NonNull
    @Provides
    LocationGateway provideLocationGateway(){
        return new LocationRepository(venueSelectedFragment);
    }

    @NonNull
    @Provides
    GalleryRecyclerAdapter provideGalleryRecyclerAdapter(){
        return new GalleryRecyclerAdapter();
    }

    @VenueSelectScope
    @NonNull
    @Provides
    VenueSelectedPresenter provideVenueSelectPresenter(CompositeDisposable compositeDisposable,
                                                       MapsModel mapsModel, LocationGateway locationGateway, ErrorHandler errorHandler, AppDatabase database) {
        return new VenueSelectedPresenter(mapsModel, compositeDisposable, locationGateway, errorHandler, database);
    }

    @VenueSelectScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }


}
