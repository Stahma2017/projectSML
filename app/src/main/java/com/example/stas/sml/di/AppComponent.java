package com.example.stas.sml.di;
import com.example.stas.sml.di.module.AppModule;
import com.example.stas.sml.di.module.ErrorHandlerModule;
import com.example.stas.sml.di.module.NetworkModule;
import com.example.stas.sml.di.module.RxModule;
import com.example.stas.sml.presentation.feature.map.di.MapsModule;
import com.example.stas.sml.presentation.feature.map.di.MapsComponent;
import com.example.stas.sml.presentation.feature.map.map.di.MapsFragmentComponent;
import com.example.stas.sml.presentation.feature.map.querysubmit.di.QueryVenuesComponent;

import javax.inject.Singleton;
import dagger.Component;

@Component(modules = {ErrorHandlerModule.class, AppModule.class, RxModule.class, NetworkModule.class})
@Singleton
public interface AppComponent {


    MapsComponent addMapsComponent();

    QueryVenuesComponent addQueryVenuesComponent();

    MapsFragmentComponent addMapsFragmentComponent();
}
