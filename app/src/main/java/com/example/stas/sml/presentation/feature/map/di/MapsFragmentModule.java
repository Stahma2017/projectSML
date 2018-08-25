package com.example.stas.sml.presentation.feature.map.di;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.stas.sml.data.mapper.VenueMapper;
import com.example.stas.sml.data.network.Api;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.di.annotations.MapsFragmentScope;
import com.example.stas.sml.domain.gateway.LocationGateway;
import com.example.stas.sml.domain.interactor.MapsModel;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.map.MapsContract;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.map.MapsPresenter;
import com.example.stas.sml.presentation.feature.map.adapter.CategoryRecyclerAdapter;
import com.example.stas.sml.presentation.feature.map.adapter.SearchSuggestionsRecyclerAdapter;
import com.example.stas.sml.presentation.feature.map.adapter.VenuesByCategoryRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class MapsFragmentModule {

    private Fragment mapsFragment;
    private CategoryRecyclerAdapter.OnItemClickListener onItemClickListenerCategory;
    private VenuesByCategoryRecyclerAdapter.OnItemClickListener onItemClickListenerByCategory;
    private SearchSuggestionsRecyclerAdapter.OnItemClickListener onItemClickListenerSuggest;

    public MapsFragmentModule(Fragment mapsFragment, CategoryRecyclerAdapter.OnItemClickListener onItemClickListenerCategory,
    VenuesByCategoryRecyclerAdapter.OnItemClickListener onItemClickListenerByCategory, SearchSuggestionsRecyclerAdapter.OnItemClickListener onItemClickListenerSuggest){
        this.mapsFragment = mapsFragment;
        this.onItemClickListenerCategory = onItemClickListenerCategory;
        this.onItemClickListenerByCategory = onItemClickListenerByCategory;
        this.onItemClickListenerSuggest = onItemClickListenerSuggest;
    }
    @NonNull
    @Provides
    CategoryRecyclerAdapter provideCategoryRecyclerAdapter(){
        return new CategoryRecyclerAdapter(onItemClickListenerCategory);
    }

    @NonNull
    @Provides
    VenuesByCategoryRecyclerAdapter provideVenuesByCategoryRecyclerAdapter(){
        return new VenuesByCategoryRecyclerAdapter(onItemClickListenerByCategory);
    }

    @NonNull
    @Provides
    SearchSuggestionsRecyclerAdapter provideSearchSuggestionsRecyclerAdapter(){
        return new SearchSuggestionsRecyclerAdapter(onItemClickListenerSuggest);
    }





    @MapsFragmentScope
    @NonNull
    @Provides
    LocationGateway provideLocationGateway(){
        return new LocationRepository(mapsFragment);
    }

    @MapsFragmentScope
    @NonNull
    @Provides
    MapsPresenter provideMapsPresenter(CompositeDisposable compositeDisposable,
                                       MapsModel mapsModel, LocationGateway locationGateway, ErrorHandler errorHandler ) {
        return new MapsPresenter(mapsModel, compositeDisposable, locationGateway, errorHandler);
    }

    @MapsFragmentScope
    @NonNull
    @Provides
    MapsModel provideMapsModel(Api api, VenueMapper venueMapper) {
        return new MapsModel(api, venueMapper);
    }
}
