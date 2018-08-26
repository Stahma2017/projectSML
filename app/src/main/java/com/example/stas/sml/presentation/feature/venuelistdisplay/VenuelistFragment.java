package com.example.stas.sml.presentation.feature.venuelistdisplay;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.stas.sml.App;
import com.example.stas.sml.Category;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.presentation.feature.map.adapter.CategoryRecyclerAdapter;
import com.example.stas.sml.R;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.map.adapter.SearchSuggestionsRecyclerAdapter;
import com.example.stas.sml.presentation.feature.venuelistdisplay.adapter.PreviousPlacesByCategoryAdapter;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import static android.content.Context.MODE_PRIVATE;


public class VenuelistFragment extends Fragment implements VenuelistContract.VenuelistView,  CategoryRecyclerAdapter.OnItemClickListener,
PreviousPlacesByCategoryAdapter.OnItemClickListener, SearchSuggestionsRecyclerAdapter.OnItemClickListener{
    private Unbinder unbinder;
    @Inject
    VenuelistPresenter presenter;
    @Inject
    SearchSuggestionsRecyclerAdapter suggestionAdapter;
    @Inject
    CategoryRecyclerAdapter categoryAdapter;
    @Inject
    PreviousPlacesByCategoryAdapter placesAdapter;
    @BindView(R.id.toolbarVenuelist)Toolbar toolbar;
    @BindView(R.id.categoryRecyclerVenuelist)RecyclerView categoryRecycler;
    @BindView(R.id.placesRecyclerPrev)RecyclerView placesRecycler;
    @BindView(R.id.search_view)SearchView searchView;
    @BindView(R.id.homeBtn)ImageButton btnHome;
    @BindView(R.id.toMapBtn)Button toMapBtn;
    @BindView(R.id.suggestion_listPrev)RecyclerView suggestionRecycler;

    public VenuelistFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        App.getInstance().addVenuelistComponent(this, this, this, this).injectVenuelistFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        searchView.setIconified(false);
        SharedPreferences prefs = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE);

        int enabledIndex = prefs.getInt("index", -1);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecycler.setLayoutManager(horizontalLayoutManager);
        categoryRecycler.setAdapter(categoryAdapter);
        categoryAdapter.setEnabledCategory(enabledIndex);
        categoryAdapter.notifyDataSetChanged();

        LinearLayoutManager placesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        placesRecycler.setLayoutManager(placesLayoutManager);
        placesRecycler.setAdapter(placesAdapter);

        LinearLayoutManager suggestionManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        suggestionRecycler.setLayoutManager(suggestionManager);
        suggestionRecycler.setAdapter(suggestionAdapter);

        if (categoryAdapter.getEnabledCategoryId() != null){
            presenter.getLocationForCategories(categoryAdapter.getEnabledCategoryId());
        }else{
            SharedPreferences qieryPrefs = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE);
            String query = qieryPrefs.getString("query", "none");
            if (!query.equals("none")){
                presenter.getLocationToSubmit(query);
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                placesAdapter.refreshList();
                presenter.getLocationToSubmit(s);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                if ((query.length() % 3 == 0) && (query.length() != 0)){
                    presenter.getTextSuggestions(query);
                }
                return true;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                return true;
            }
        });

        btnHome.setOnClickListener(view12 -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE).edit();
            int categoryIndex =  categoryAdapter.getEnabledCategory();
            editor.putInt("toMap", categoryIndex);
            editor.apply();
            MainActivity activity = (MainActivity) getActivity();
            activity.showToolbar();
            activity.displayMapsFragment();
        });

        toMapBtn.setOnClickListener(view1 -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE).edit();
            int categoryIndex =  categoryAdapter.getEnabledCategory();
            editor.putInt("toMap", categoryIndex);
            editor.apply();
            MainActivity activity = (MainActivity) getActivity();
            activity.showToolbar();
            activity.displayMapsFragment();
        });
        return view;
    }

    @Override
    public void deliverLocationForpreveious(Location location, String categoryId) {
        presenter.getVenuesWithCategory(categoryId, location);
    }


    @Override
    public void deliverLocationToQuerySumbit(Location location, String query) {
        categoryAdapter.setEnabledCategory(-1);
        categoryAdapter.notifyDataSetChanged();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE).edit();
        editor.putString("query", query);
        editor.apply();
        presenter.getVenuesBuSubmit(location, query);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
        App.getInstance().clearVenuComponent();
    }

    @Override
    public void onItemClick(Category category) {
        categoryAdapter.refreshList();
        category.setEnabled(true);
        categoryAdapter.notifyDataSetChanged();
        presenter.getLocationForCategories(category.getCategoryId());
    }

    @Override
    public void onItemClick(VenueEntity venue) {
        String venueId = venue.getId();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE).edit();
        editor.putString("venueSelect", venueId);
        editor.apply();
        MainActivity activity = (MainActivity) getActivity();
        activity.displayVenueSelectedFragment();
    }

    @Override
    public void showPlacesByCategory(List<VenueEntity> venues) {
        placesAdapter.setVenues(venues);
        placesAdapter.notifyDataSetChanged();
        placesRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchSuggestions(List<Minivenue> minivenues) {
        suggestionRecycler.setVisibility(View.VISIBLE);
        suggestionAdapter.setMinivenues(minivenues);
        suggestionAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPlacesBySubmit(List<VenueEntity> venues) {
        placesAdapter.setVenues(venues);
        placesAdapter.notifyDataSetChanged();
        placesRecycler.setVisibility(View.VISIBLE);
        suggestionRecycler.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Minivenue minivenue) {
        String venueId = minivenue.getId();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE).edit();
        editor.putString("venueSelect", venueId);
        editor.apply();
        MainActivity activity = (MainActivity) getActivity();
        activity.displayVenueSelectedFragment();
    }


}
