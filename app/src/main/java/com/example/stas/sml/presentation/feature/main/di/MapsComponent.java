package com.example.stas.sml.presentation.feature.main.di;

import com.example.stas.sml.di.annotations.MapsScope;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.map.di.MapsFragmentComponent;
import com.example.stas.sml.presentation.feature.map.di.MapsFragmentModule;

import dagger.Subcomponent;

@Subcomponent(modules = {MapsModule.class})
@MapsScope
public interface MapsComponent {
    void injectMainActivity(MainActivity mainActivity);
    MapsFragmentComponent addMapsFragmentComponent(MapsFragmentModule mapsFragmentModule);
}

