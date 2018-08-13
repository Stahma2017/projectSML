package com.example.stas.sml.presentation.feature.map.querysubmit.di;

import com.example.stas.sml.di.annotations.QueryVenuesScope;
import com.example.stas.sml.presentation.feature.map.querysubmit.VenuesByQuerySubmitFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {QueryVenuesModule.class})
@QueryVenuesScope
public interface QueryVenuesComponent {
    void injectVenuesByQuerySubmitFragment(VenuesByQuerySubmitFragment fragment);
}
