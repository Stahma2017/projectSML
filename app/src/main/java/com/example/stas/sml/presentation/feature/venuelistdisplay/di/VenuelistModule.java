package com.example.stas.sml.presentation.feature.venuelistdisplay.di;

import android.support.v4.app.Fragment;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.di.annotations.VenuelistFragmentScope;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.map.adapter.CategoryRecyclerAdapter;
import com.example.stas.sml.presentation.feature.map.adapter.SearchSuggestionsRecyclerAdapter;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistPresenter;
import com.example.stas.sml.presentation.feature.venuelistdisplay.adapter.PreviousPlacesByCategoryAdapter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class VenuelistModule {

    private Fragment venulistFragment;

    private PreviousPlacesByCategoryAdapter.OnItemClickListener onItemClickListener;
    private SearchSuggestionsRecyclerAdapter.OnItemClickListener onItemClickListenerSuggest;
    private CategoryRecyclerAdapter.OnItemClickListener onItemClickListenerCategory;

    public VenuelistModule(VenuelistFragment venulistFragment, PreviousPlacesByCategoryAdapter.OnItemClickListener onItemClickListener,
                           CategoryRecyclerAdapter.OnItemClickListener onItemClickListenerCategory,
                           SearchSuggestionsRecyclerAdapter.OnItemClickListener onItemClickListenerSuggest){
        this.venulistFragment = venulistFragment;
        this.onItemClickListener = onItemClickListener;
        this.onItemClickListenerCategory = onItemClickListenerCategory;
        this.onItemClickListenerSuggest = onItemClickListenerSuggest;
    }

    @VenuelistFragmentScope
    @NonNull
    @Provides
    LocationGateway provideLocationGateway(){
        return new LocationRepository(venulistFragment);
    }


    @NonNull
    @Provides
    PreviousPlacesByCategoryAdapter providePlacesAdapter(){
        return new PreviousPlacesByCategoryAdapter(onItemClickListener);
    }

    @NonNull
    @Provides
    SearchSuggestionsRecyclerAdapter provideSuggestAdapter(){
        return new SearchSuggestionsRecyclerAdapter(onItemClickListenerSuggest);
    }

    @NonNull
    @Provides
    CategoryRecyclerAdapter provideCategoryAdapter(){
        return new CategoryRecyclerAdapter(onItemClickListenerCategory);
    }

    @VenuelistFragmentScope
    @NonNull
    @Provides
    VenuelistPresenter provideMapsPresenter(CompositeDisposable compositeDisposable,
                                            MapsModel mapsModel, LocationGateway locationGateway, ErrorHandler errorHandler) {
        return new VenuelistPresenter(mapsModel, compositeDisposable, locationGateway, errorHandler);
    }

    @VenuelistFragmentScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }

}
