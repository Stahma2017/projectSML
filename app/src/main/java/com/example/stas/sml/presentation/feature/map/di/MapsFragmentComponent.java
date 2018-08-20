package com.example.stas.sml.presentation.feature.map.di;


import com.example.stas.sml.di.annotations.MapsFragmentScope;
import com.example.stas.sml.presentation.feature.map.MapsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {MapsFragmentModule.class})
@MapsFragmentScope
public interface MapsFragmentComponent {

    void injectMapsFragment(MapsFragment mapsFragment);

}
