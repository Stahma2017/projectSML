package com.example.stas.sml.presentation.feature.save.di;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.stas.sml.data.database.AppDatabase;
import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.di.annotations.SaveScope;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.save.SavePresenter;
import com.example.stas.sml.presentation.feature.save.adapter.SaveRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class SaveModule {
    private Fragment saveFragment;
    private SaveRecyclerAdapter.OnItemClickListener onItemClickListener;

    public SaveModule(Fragment saveFragment, SaveRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.saveFragment = saveFragment;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Provides
    SaveRecyclerAdapter provideSaveRecyclerAdapter(){
        return new SaveRecyclerAdapter(onItemClickListener);
    }

    @SaveScope
    @NonNull
    @Provides
    LocationGateway provideLocationGateway(){
        return new LocationRepository(saveFragment);
    }

    @SaveScope
    @NonNull
    @Provides
    SavePresenter provideSavePresenter(CompositeDisposable compositeDisposable,
                                       MapsModel mapsModel, LocationGateway locationGateway, ErrorHandler errorHandler, AppDatabase database) {
        return new SavePresenter(mapsModel, compositeDisposable, locationGateway, errorHandler, database);
    }

    @SaveScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }




}
