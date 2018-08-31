package com.example.stas.sml;

import android.app.Application;

import com.example.stas.sml.di.AppComponent;
import com.example.stas.sml.di.DaggerAppComponent;
import com.example.stas.sml.presentation.feature.history.HistoryFragment;
import com.example.stas.sml.presentation.feature.history.adapter.HistoryRecyclerAdapter;
import com.example.stas.sml.presentation.feature.history.di.HistoryComponent;
import com.example.stas.sml.presentation.feature.history.di.HistoryModule;
import com.example.stas.sml.presentation.feature.main.di.MapsComponent;
import com.example.stas.sml.di.module.AppModule;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.map.adapter.CategoryRecyclerAdapter;
import com.example.stas.sml.presentation.feature.map.adapter.SearchSuggestionsRecyclerAdapter;
import com.example.stas.sml.presentation.feature.map.adapter.VenuesByCategoryRecyclerAdapter;
import com.example.stas.sml.presentation.feature.map.di.MapsFragmentComponent;
import com.example.stas.sml.presentation.feature.map.di.MapsFragmentModule;
import com.example.stas.sml.presentation.feature.save.SaveFragment;
import com.example.stas.sml.presentation.feature.save.adapter.SaveRecyclerAdapter;
import com.example.stas.sml.presentation.feature.save.di.SaveComponent;
import com.example.stas.sml.presentation.feature.save.di.SaveModule;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;
import com.example.stas.sml.presentation.feature.venuelistdisplay.adapter.PreviousPlacesByCategoryAdapter;
import com.example.stas.sml.presentation.feature.venuelistdisplay.di.VenuelistComponent;
import com.example.stas.sml.presentation.feature.venuelistdisplay.di.VenuelistModule;
import com.example.stas.sml.presentation.feature.venueselected.VenueSelectedFragment;
import com.example.stas.sml.presentation.feature.venueselected.di.VenueSelectedComponent;
import com.example.stas.sml.presentation.feature.venueselected.di.VenueSelectedModule;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

    protected static App instance;

    public static App getInstance() {
        return instance;
    }

    private AppComponent component;
    private MapsComponent mapsComponent;


    private MapsFragmentComponent mapsFragmentComponent;
    private VenuelistComponent venuelistComponent;
    private VenueSelectedComponent venueSelectedComponent;
    private HistoryComponent historyComponent;
    private SaveComponent saveComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }else {
            LeakCanary.install(this);
        }

    }

    public void clearAllComponents() {
        mapsFragmentComponent = null;
        venuelistComponent = null;
        venueSelectedComponent = null;
        historyComponent = null;
        saveComponent = null;
    }


    public AppComponent getComponent() {
        return component;
    }

    public MapsComponent getMapsComponent() {
        return mapsComponent;
    }

    public MapsComponent addMapsComponent() {
        if (mapsComponent == null) {
            mapsComponent = component.addMapsComponent();
        }
        return mapsComponent;
    }

    public void clearMapsComponent() {
        mapsComponent = null;
    }

    public MapsFragmentComponent addMapsFragmentComponent(MapsFragment mapsFragment, CategoryRecyclerAdapter.OnItemClickListener onItemClickListenerCategory,
                                                          VenuesByCategoryRecyclerAdapter.OnItemClickListener onItemClickListenerByCategory,
                                                          SearchSuggestionsRecyclerAdapter.OnItemClickListener onItemClickListenerSuggest) {
        if (mapsFragmentComponent == null) {
            mapsFragmentComponent = mapsComponent.addMapsFragmentComponent(new MapsFragmentModule(mapsFragment, onItemClickListenerCategory,
                    onItemClickListenerByCategory, onItemClickListenerSuggest));
        }
        return mapsFragmentComponent;
    }

    public void clearMapsFragmentComponent() {
        mapsFragmentComponent = null;
    }

    public VenuelistComponent addVenuelistComponent(VenuelistFragment venuelistFragment, PreviousPlacesByCategoryAdapter.OnItemClickListener onItemClickListener,
                                                    CategoryRecyclerAdapter.OnItemClickListener onItemClickListenerCategory,
                                                    SearchSuggestionsRecyclerAdapter.OnItemClickListener onItemClickListenerSuggest) {
        if (venuelistComponent == null) {
            venuelistComponent = mapsComponent.addVenuelistComponent(new VenuelistModule(venuelistFragment, onItemClickListener,
                    onItemClickListenerCategory, onItemClickListenerSuggest));
        }
        return venuelistComponent;
    }

    public void clearVenuComponent() {
        venuelistComponent = null;
    }

    public VenueSelectedComponent addVenueSelectComponent(VenueSelectedFragment venueSelectedFragment) {
        if (venueSelectedComponent == null) {
            venueSelectedComponent = mapsComponent.addVenueSelectedComponent(new VenueSelectedModule(venueSelectedFragment));
        }
        return venueSelectedComponent;
    }

    public void clearVenueSelectComponent() {
        venueSelectedComponent = null;
    }

    public HistoryComponent addHistoryComponent(HistoryFragment historyFragment, HistoryRecyclerAdapter.OnItemClickListener onItemClickListener) {
        if (historyComponent == null) {
            historyComponent = mapsComponent.addHistoryComponent(new HistoryModule(historyFragment, onItemClickListener));
        }
        return historyComponent;
    }

    public void clearHistoryComponent() {
        historyComponent = null;
    }

    public SaveComponent addSaveComponent(SaveFragment saveFragment, SaveRecyclerAdapter.OnItemClickListener onItemClickListener) {
        if (saveComponent == null) {
            saveComponent = mapsComponent.addSaveComponent(new SaveModule(saveFragment, onItemClickListener));
        }
        return saveComponent;
    }

    public void clearSaveComponent() {
        saveComponent = null;
    }


}