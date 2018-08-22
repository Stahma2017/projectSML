package com.example.stas.sml.presentation.feature.venueselected.di;

import com.example.stas.sml.di.annotations.VenueSelectScope;
import com.example.stas.sml.presentation.feature.venueselected.VenueSelectedFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {VenueSelectedModule.class})
@VenueSelectScope
public interface VenueSelectedComponent {

    void injectVenueSelectedFragment(VenueSelectedFragment venueSelectedFragment);

}
