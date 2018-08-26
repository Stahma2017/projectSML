package com.example.stas.sml.di;
import com.example.stas.sml.di.module.AppModule;
import com.example.stas.sml.di.module.DatabaseModule;
import com.example.stas.sml.di.module.ErrorHandlerModule;
import com.example.stas.sml.di.module.NetworkModule;
import com.example.stas.sml.di.module.RxModule;
import com.example.stas.sml.presentation.feature.main.di.MapsComponent;


import javax.inject.Singleton;
import dagger.Component;

@Component(modules = {ErrorHandlerModule.class, AppModule.class, RxModule.class, NetworkModule.class, DatabaseModule.class})
@Singleton
public interface AppComponent {
    MapsComponent addMapsComponent();
}
