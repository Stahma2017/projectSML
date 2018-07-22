package com.example.stas.sml.di;

import com.example.stas.sml.di.module.AppModule;
import com.example.stas.sml.di.module.ErrorHandlerModule;
import com.example.stas.sml.di.module.PresenterModule;
import com.example.stas.sml.presentation.feature.map.MapsActivity;

import javax.inject.Singleton;
import dagger.Component;

@Component(modules = {PresenterModule.class, ErrorHandlerModule.class, AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MapsActivity mapsActivity);
}
