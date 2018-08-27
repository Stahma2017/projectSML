package com.example.stas.sml.presentation.feature.save.di;

import com.example.stas.sml.di.annotations.SaveScope;
import com.example.stas.sml.presentation.feature.save.SaveFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {SaveModule.class})
@SaveScope
public interface SaveComponent {
    void injectSaveFragment(SaveFragment saveFragment);
}
