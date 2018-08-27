package com.example.stas.sml.presentation.feature.history.di;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.stas.sml.data.database.AppDatabase;
import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.di.annotations.HistoryScope;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.history.HistoryPresenter;
import com.example.stas.sml.presentation.feature.history.adapter.HistoryRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class HistoryModule {

    private Fragment historyFragment;
    private HistoryRecyclerAdapter.OnItemClickListener onItemClickListener;

    public HistoryModule(Fragment historyFragment, HistoryRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.historyFragment = historyFragment;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Provides
    HistoryRecyclerAdapter provideHistoryRecyclerAdapter(){
        return new HistoryRecyclerAdapter(onItemClickListener);
    }


    @HistoryScope
    @NonNull
    @Provides
    LocationGateway provideLocationGateway(){
        return new LocationRepository(historyFragment);
    }

    @HistoryScope
    @NonNull
    @Provides
    HistoryPresenter provideHistoryPresenter(CompositeDisposable compositeDisposable,
                                             MapsModel mapsModel, LocationGateway locationGateway, ErrorHandler errorHandler, AppDatabase database) {
        return new HistoryPresenter(mapsModel, compositeDisposable, locationGateway, errorHandler, database);
    }

    @HistoryScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }
}
