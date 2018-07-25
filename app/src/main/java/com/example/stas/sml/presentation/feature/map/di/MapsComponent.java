package com.example.stas.sml.presentation.feature.map.di;

import com.example.stas.sml.di.annotations.MapsScope;
import com.example.stas.sml.presentation.feature.map.MapsActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {MapsModule.class})
@MapsScope
public interface MapsComponent {
    void injectMapsActivity(MapsActivity mapsActivity);

}

