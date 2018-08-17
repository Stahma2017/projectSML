package com.example.stas.sml.presentation.feature.main.di;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.di.annotations.MapsScope;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.main.ActivityContract;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.main.MainActivityPresenter;
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
}
