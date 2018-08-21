package com.example.stas.sml.presentation.feature.venuelistdisplay;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.stas.sml.App;
import com.example.stas.sml.Category;
import com.example.stas.sml.CategoryRecyclerAdapter;
import com.example.stas.sml.R;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.venuelistdisplay.adapter.PreviousPlacesByCategoryAdapter;
import com.example.stas.sml.service.GpsTracker;

import java.security.Provider;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;


public class VenuelistFragment extends Fragment implements VenuelistContract.VenuelistView,  CategoryRecyclerAdapter.OnItemClickListener,
PreviousPlacesByCategoryAdapter.OnItemClickListener{


    private Unbinder unbinder;
    public static String CATEGORY_PREFS = "category_pref";

    @Inject
    VenuelistPresenter presenter;

    //New dependency
    private CategoryRecyclerAdapter categoryAdapter;

    //New dependency
    private PreviousPlacesByCategoryAdapter placesAdapter;



    @BindView(R.id.toolbarVenuelist)Toolbar toolbar;
    @BindView(R.id.categoryRecyclerVenuelist)RecyclerView categoryRecycler;
    @BindView(R.id.placesRecyclerPrev)RecyclerView placesRecycler;
    @BindView(R.id.search_view)SearchView searchView;

    @BindView(R.id.homeBtn)ImageButton btnHome;

    public VenuelistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        App.getInstance().addVenuelistComponent(this).injectVenuelistFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        searchView.setIconified(false);


       SharedPreferences prefs = getActivity().getSharedPreferences(CATEGORY_PREFS, MODE_PRIVATE);
        int enabledIndex = prefs.getInt("index", -1);

        categoryAdapter = new CategoryRecyclerAdapter(this);
        categoryAdapter.setEnabledCategory(enabledIndex);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecycler.setLayoutManager(horizontalLayoutManager);
        categoryRecycler.setAdapter(categoryAdapter);

        placesAdapter = new PreviousPlacesByCategoryAdapter(this);
        LinearLayoutManager placesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        placesRecycler.setLayoutManager(placesLayoutManager);
        placesRecycler.setAdapter(placesAdapter);

        if (categoryAdapter.getEnabledCategoryId() != null){
            presenter.getLocationForCategories(categoryAdapter.getEnabledCategoryId());
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {


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

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.showToolbar();
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }



    @Override
    public void deliverLocationForpreveious(Location location, String categoryId) {
        presenter.getVenuesWithCategory(categoryId, location);

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        App.getInstance().clearVenuComponent();
        presenter.detachView();
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

    }

    @Override
    public void showPreviousCategories(Location location) {

    }

    @Override
    public void showPlacesByCategory(List<VenueEntity> venues) {
        placesAdapter.setVenues(venues);
        placesAdapter.notifyDataSetChanged();
        placesRecycler.setVisibility(View.VISIBLE);
    }

    //delete after implementing search query
   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, toolbar.getMenu());

        SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
           *//*     suggestionRecycler.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new VenuesByQuerySubmitFragment());
                fragmentTransaction.commit();*//*
                *//*     presenter.getVenuesByQuerySubmit(s);*//*
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                *//*if (s.length() >= 3){
                    presenter.getTextSuggestions(s);
                }*//*
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
               *//* toolbar.setBackgroundColor(Color.WHITE);
                categoryRecycler.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
                toolbar.startAnimation(anim);
                categoryRecycler.startAnimation(anim);*//*
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
              *//*  suggestionRecycler.setVisibility(View.GONE);
                toolbar.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_14_edited));
                categoryRecycler.setVisibility(View.GONE);
                locationBtn.setVisibility(View.VISIBLE);
                zoomInBtn.setVisibility(View.VISIBLE);
                zoomOutBtn.setVisibility(View.VISIBLE);*//*
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }*/

}
