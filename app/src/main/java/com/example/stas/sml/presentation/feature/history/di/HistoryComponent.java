package com.example.stas.sml.presentation.feature.history.di;

import com.example.stas.sml.di.annotations.HistoryScope;
import com.example.stas.sml.presentation.feature.history.HistoryFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {HistoryModule.class})
@HistoryScope
public interface HistoryComponent {
    void injectHistoryFragment(HistoryFragment historyFragment);
}
