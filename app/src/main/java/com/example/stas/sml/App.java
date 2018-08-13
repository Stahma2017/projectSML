package com.example.stas.sml;
import android.app.Application;

import com.example.stas.sml.di.AppComponent;
import com.example.stas.sml.di.DaggerAppComponent;
import com.example.stas.sml.presentation.feature.map.di.MapsComponent;
import com.example.stas.sml.di.module.AppModule;
import com.example.stas.sml.presentation.feature.map.di.MapsModule;
import com.example.stas.sml.presentation.feature.map.map.di.MapsFragmentComponent;
import com.example.stas.sml.presentation.feature.map.querysubmit.di.QueryVenuesComponent;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

    protected static App instance;

    public static App getInstance() {
        return instance;
    }

    private  AppComponent component;
    private MapsComponent mapsComponent;

    private QueryVenuesComponent queryVenuesComponent;
    private MapsFragmentComponent mapsFragmentComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }


    public AppComponent getComponent() {
        return component;
    }

    public MapsComponent getMapsComponent() {
        return mapsComponent;
    }

    public MapsComponent addMapsComponent(){
        if (mapsComponent == null){
            mapsComponent = component.addMapsComponent();
        }
        return mapsComponent;
    }

    public void clearMapsComponent(){
        mapsComponent = null;
    }

    public QueryVenuesComponent addQueryVenuesComponent(){
        if(queryVenuesComponent == null){
            queryVenuesComponent = component.addQueryVenuesComponent();
        }
        return queryVenuesComponent;
    }

    public void clearQueryVenuesComponent(){
        queryVenuesComponent = null;
    }

    public MapsFragmentComponent addMapsFragmentComponent(){
        if (mapsFragmentComponent == null){
            mapsFragmentComponent = component.addMapsFragmentComponent();
        }
        return mapsFragmentComponent;
    }

    public void clearMapsFragmentComponent(){
        mapsFragmentComponent = null;
    }

}