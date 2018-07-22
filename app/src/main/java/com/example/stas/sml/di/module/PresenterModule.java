package com.example.stas.sml.di.module;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.map.MapsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class PresenterModule {

    @Singleton
    @NonNull
    @Provides MapsPresenter provideMapsPresenter(ErrorHandler errorHandler){
        return new MapsPresenter(errorHandler);
    }

}
