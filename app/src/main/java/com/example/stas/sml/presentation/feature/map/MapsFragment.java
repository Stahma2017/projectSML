package com.example.stas.sml.presentation.feature.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stas.sml.App;
import com.example.stas.sml.Category;
import com.example.stas.sml.CategoryRecyclerAdapter;
import com.example.stas.sml.R;
import com.example.stas.sml.presentation.feature.map.adapter.SearchSuggestionsRecyclerAdapter;
import com.example.stas.sml.presentation.feature.map.adapter.VenuesByCategoryRecyclerAdapter;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;


public class MapsFragment extends Fragment implements MapsContract.MapsView, OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener,
        CategoryRecyclerAdapter.OnItemClickListener, VenuesByCategoryRecyclerAdapter.OnItemClickListener, SearchSuggestionsRecyclerAdapter.OnItemClickListener
{
    public static String MY_PREFS = "mapsPreferences";

    GoogleMap map;
    private Marker marker;
    private BottomSheetBehavior bottomSheetBehavior;
    private Unbinder unbinder;


    @Inject
    MapsPresenter presenter;

    //New dependency
    private CategoryRecyclerAdapter categoryAdapter;

    //New dependency
    private VenuesByCategoryRecyclerAdapter placesAdapter;

    //New dependency
    private SearchSuggestionsRecyclerAdapter suggestionAdapter;

    @BindView(R.id.addressField)TextView addressTW;
    @BindView(R.id.regionField)TextView regionTW;
    @BindView(R.id.distance)TextView distanceTW;
    @BindView(R.id.bottom_sheet)View bottomSheet;
    @BindView(R.id.locationBtn)Button locationBtn;
    @BindView(R.id.minusBtn)Button zoomOutBtn;
    @BindView(R.id.plusBtn)Button zoomInBtn;
    @BindView(R.id.myTool)Toolbar toolbar;
    @BindView(R.id.categoryRecycler)RecyclerView categoryRecycler;
    @BindView(R.id.placesRecycler)RecyclerView placesRecycler;
    @BindView(R.id.suggestion_list)RecyclerView suggestionRecycler;
    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.toVenueListBtn)Button venueListBtn;
    @BindView(R.id.map)MapView mapView;

    @BindView(R.id.search_viewMaps)SearchView searchView;
    @BindView(R.id.homeBtnMaps)ImageButton btnHome;

    public MapsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        App.getInstance().addMapsFragmentComponent(this).injectMapsFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        locationBtn.setOnClickListener(this);
        zoomOutBtn.setOnClickListener(this);
        zoomInBtn.setOnClickListener(this);
        venueListBtn.setOnClickListener(this);

        categoryAdapter = new CategoryRecyclerAdapter(this);
        categoryAdapter.refreshList();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecycler.setLayoutManager(horizontalLayoutManager);
        categoryRecycler.setAdapter(categoryAdapter);

        placesAdapter = new VenuesByCategoryRecyclerAdapter(this);
        LinearLayoutManager placesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        placesRecycler.setLayoutManager(placesLayoutManager);
        placesRecycler.setAdapter(placesAdapter);

        suggestionAdapter = new SearchSuggestionsRecyclerAdapter(this);
        LinearLayoutManager suggestionManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        suggestionRecycler.setLayoutManager(suggestionManager);
        suggestionRecycler.setAdapter(suggestionAdapter);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    toolbar.setBackgroundColor(Color.WHITE);
                    btnHome.setVisibility(View.VISIBLE);
                    categoryRecycler.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
                    toolbar.startAnimation(anim);
                    categoryRecycler.startAnimation(anim);
                }else {
                    searchView.setIconified(true);
                    toolbar.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_14_edited));
                    btnHome.setVisibility(View.GONE);
                    categoryRecycler.setVisibility(View.GONE);
                    suggestionRecycler.setVisibility(View.GONE);
                }
            }
        });

        btnHome.setOnClickListener(view1 -> searchView.setIconified(true));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() >= 3){
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
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS, MODE_PRIVATE);
            int enabledIndex = prefs.getInt("toMap", -1);

            if (enabledIndex != -1) {
                categoryAdapter.refreshList();
                categoryAdapter.setEnabledCategory(enabledIndex);
                categoryAdapter.notifyDataSetChanged();
                presenter.getLocationForCategories(categoryAdapter.getEnabledCategoryId());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        App.getInstance().clearMapsComponent();
        presenter.detachView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnMapClickListener(this);
        map.getUiSettings().setCompassEnabled(false);
        CameraPosition liberty = CameraPosition.builder().target(new LatLng(45.045583, 38.978452)).zoom(16).bearing(0).tilt(45).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        setMarker(latLng);
        presenter.getLocation(latLng);
    }

    @Override
    public void showBottomSheet(Location location, LatLng latLng) {
        List<Address> addresses = getAddress(latLng);
        float[] results = new float[1];
        Location.distanceBetween(latLng.latitude, latLng.longitude, location.getLatitude(), location.getLongitude(), results);
        String[] splitedAddress = addresses.get(0).getAddressLine(0).split(",");
        String address = splitedAddress[0] + "," + splitedAddress[1];
        String region = addresses.get(0).getCountryName() + ", " + addresses.get(0).getAdminArea();
        addressTW.setText(address);
        regionTW.setText(region);
        distanceTW.setText(String.format(Locale.CANADA,"%.0f м", results[0]));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private List<Address> getAddress(LatLng latLng){
        List<Address> addresses = new ArrayList<>();

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    void setMarker(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions().
                position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
        marker = map.addMarker(markerOptions);
    }

    @Override
    public void toCurrentLocation(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.locationBtn:
                presenter.getLocationForLocationButton();
                break;
            case R.id.minusBtn:
                map.animateCamera(CameraUpdateFactory.zoomOut());
                break;
            case R.id.plusBtn:
                map.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.toVenueListBtn:
                int categoryIndex = categoryAdapter.getEnabledCategory();
                if (categoryIndex != -1) {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE).edit();
                    editor.putInt("index", categoryIndex);
                    editor.apply();
                }
                MainActivity activity = (MainActivity) getActivity();
                activity.hideToolbar();
                activity.displayVenuelistFragment();
                break;
        }
    }

    @Override
    public void showPlacesByCategory(List<VenueEntity> venues) {
        placesAdapter.setVenues(venues);
        placesAdapter.notifyDataSetChanged();
        placesRecycler.setVisibility(View.VISIBLE);
        venueListBtn.setVisibility(View.VISIBLE);
        hideProgressbar();
        locationBtn.setVisibility(View.GONE);
        zoomOutBtn.setVisibility(View.GONE);
        zoomInBtn.setVisibility(View.GONE);
    }

    @Override
    public void showSearchSuggestions(List<Minivenue> minivenues){
        suggestionRecycler.setVisibility(View.VISIBLE);
        suggestionAdapter.setMinivenues(minivenues);
        suggestionAdapter.notifyDataSetChanged();
    }

    public void deliverLocationToCategories(Location location, String category){
        presenter.getVenuesWithCategory(category, location);
        displayProgressbar();
    }

    @Override
    public void onItemClick(Category category) {
        categoryAdapter.refreshList();
        category.setEnabled(true);
        categoryAdapter.notifyDataSetChanged();
        suggestionRecycler.setVisibility(View.GONE);
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
    public void onItemClick(Minivenue minivenue) {
        //handle click on suggestion
    }

    private void displayProgressbar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
