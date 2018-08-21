package com.example.stas.sml;
import android.app.Application;

import com.example.stas.sml.di.AppComponent;
import com.example.stas.sml.di.DaggerAppComponent;
import com.example.stas.sml.presentation.feature.main.di.MapsComponent;
import com.example.stas.sml.di.module.AppModule;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.map.di.MapsFragmentComponent;
import com.example.stas.sml.presentation.feature.map.di.MapsFragmentModule;
import com.example.stas.sml.presentation.feature.querysubmit.di.QueryVenuesComponent;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;
import com.example.stas.sml.presentation.feature.venuelistdisplay.di.VenuelistComponent;
import com.example.stas.sml.presentation.feature.venuelistdisplay.di.VenuelistModule;
import com.squareup.haha.guava.collect.Maps;
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
    private VenuelistComponent venuelistComponent;

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

    public MapsFragmentComponent addMapsFragmentComponent(MapsFragment mapsFragment){
        if (mapsFragmentComponent == null){
            mapsFragmentComponent = mapsComponent.addMapsFragmentComponent(new MapsFragmentModule(mapsFragment));
        }
        return mapsFragmentComponent;
    }

    public void clearMapsFragmentComponent(){
        mapsFragmentComponent = null;
    }

    public VenuelistComponent addVenuelistComponent(VenuelistFragment venuelistFragment){
        if (venuelistComponent == null){
            venuelistComponent = mapsComponent.addVenuelistComponent(new VenuelistModule(venuelistFragment));
        }
        return venuelistComponent;
    }

    public void clearVenuComponent(){
        venuelistComponent = null;
    }

}