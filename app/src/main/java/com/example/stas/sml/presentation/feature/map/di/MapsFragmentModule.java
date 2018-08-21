package com.example.stas.sml.presentation.feature.map.di;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.di.annotations.MapsFragmentScope;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.map.MapsContract;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.map.MapsPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class MapsFragmentModule {

    private Fragment mapsFragment;

    public MapsFragmentModule(Fragment mapsFragment){
        this.mapsFragment = mapsFragment;
    }

    @MapsFragmentScope
    @NonNull
    @Provides
    LocationGateway provideLocationGateway(){
        return new LocationRepository(mapsFragment);
    }

    @MapsFragmentScope
    @NonNull
    @Provides
    MapsPresenter provideMapsPresenter(CompositeDisposable compositeDisposable,
                                       MapsModel mapsModel, LocationGateway locationGateway, ErrorHandler errorHandler ) {
        return new MapsPresenter(mapsModel, compositeDisposable, locationGateway, errorHandler);
    }

    @MapsFragmentScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }
}
