package com.example.stas.sml.presentation.feature.main.di;

import com.example.stas.sml.di.annotations.MapsScope;
import com.example.stas.sml.presentation.feature.history.di.HistoryComponent;
import com.example.stas.sml.presentation.feature.history.di.HistoryModule;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.map.di.MapsFragmentComponent;
import com.example.stas.sml.presentation.feature.map.di.MapsFragmentModule;
import com.example.stas.sml.presentation.feature.save.di.SaveComponent;
import com.example.stas.sml.presentation.feature.save.di.SaveModule;
import com.example.stas.sml.presentation.feature.venuelistdisplay.di.VenuelistComponent;
import com.example.stas.sml.presentation.feature.venuelistdisplay.di.VenuelistModule;
import com.example.stas.sml.presentation.feature.venueselected.di.VenueSelectedComponent;
import com.example.stas.sml.presentation.feature.venueselected.di.VenueSelectedModule;

import dagger.Subcomponent;

@Subcomponent(modules = {MapsModule.class})
@MapsScope
public interface MapsComponent {
    void injectMainActivity(MainActivity mainActivity);
    MapsFragmentComponent addMapsFragmentComponent(MapsFragmentModule mapsFragmentModule);
    VenuelistComponent addVenuelistComponent(VenuelistModule venuelistModule);
    VenueSelectedComponent addVenueSelectedComponent(VenueSelectedModule venueSelectedModule);
    HistoryComponent addHistoryComponent(HistoryModule historyModule);
    SaveComponent addSaveComponent(SaveModule saveModule);

}

