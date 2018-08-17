package com.example.stas.sml.di.module;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class AppModule {
    private Context appContext;

    public AppModule(@NonNull Context context){
        appContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return appContext;
    }

    @Provides
    @Singleton
    public LocationManager provideLocationManager(Context context){
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

}