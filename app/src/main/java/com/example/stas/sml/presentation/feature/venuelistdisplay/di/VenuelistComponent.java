package com.example.stas.sml.presentation.feature.venuelistdisplay.di;

import com.example.stas.sml.di.annotations.VenuelistFragmentScope;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {VenuelistModule.class})
@VenuelistFragmentScope
public interface VenuelistComponent {

    void injectVenuelistFragment(VenuelistFragment venuelistFragment);
}
