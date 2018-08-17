package com.example.stas.sml.presentation.feature.map;

import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.stas.sml.App;
import com.example.stas.sml.Category;
import com.example.stas.sml.CategoryRecyclerAdapter;
import com.example.stas.sml.R;
import com.example.stas.sml.SearchSuggestionsRecyclerAdapter;
import com.example.stas.sml.VenuesByCategoryRecyclerAdapter;
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
import static android.content.Context.LOCATION_SERVICE;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener,
        CategoryRecyclerAdapter.OnItemClickListener, VenuesByCategoryRecyclerAdapter.OnItemClickListener, SearchSuggestionsRecyclerAdapter.OnItemClickListener{

    GoogleMap map;


    private Marker marker;
    private BottomSheetBehavior bottomSheetBehavior;
    private Unbinder unbinder;

    @Inject
    MapsPresenter presenter;

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

    public MapsFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        App.getInstance().addMapsFragmentComponent().injectMapsFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        locationBtn.setOnClickListener(this);
        zoomOutBtn.setOnClickListener(this);
        zoomInBtn.setOnClickListener(this);
        venueListBtn.setOnClickListener(this);

        CategoryRecyclerAdapter categoryAdapter = new CategoryRecyclerAdapter(this);
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

        return view;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, toolbar.getMenu());

        SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
           /*     suggestionRecycler.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new VenuesByQuerySubmitFragment());
                fragmentTransaction.commit();*/
           /*     presenter.getVenuesByQuerySubmit(s);*/
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() >= 3){
                    presenter.getTextSuggestions(s);
                }
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                toolbar.setBackgroundColor(Color.WHITE);
                categoryRecycler.setVisibility(View.VISIBLE);
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                toolbar.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rectangle_14_edited));
                categoryRecycler.setVisibility(View.GONE);
                suggestionRecycler.setVisibility(View.GONE);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnMapClickListener(this);
        CameraPosition liberty = CameraPosition.builder().target(new LatLng(45.045583, 38.978452)).zoom(16).bearing(0).tilt(45).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        setMarker(latLng);

        List<Address> addresses = getAddress(latLng);
        Location location = getCurrentLocation();
        float[] results = new float[1];
        Location.distanceBetween(latLng.latitude, latLng.longitude, location.getLatitude (), location.getLongitude(), results);
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

    public Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        return locationManager.getLastKnownLocation(provider);
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

    private void toCurrentLocation() {
        Location location = getCurrentLocation();
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
                toCurrentLocation();
                break;
            case R.id.minusBtn:
                map.animateCamera(CameraUpdateFactory.zoomOut());
                break;
            case R.id.plusBtn:
                map.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.toVenueListBtn:
                MainActivity activity = (MainActivity) getActivity();
                activity.displayVenuelistFragment();
                break;
        }
    }

    public void showPlacesByCategory(VenueEntity venue) {

        placesAdapter.clearList();
        placesAdapter.insertVenue(venue);
        placesAdapter.notifyDataSetChanged();
        placesRecycler.setVisibility(View.VISIBLE);

        locationBtn.setVisibility(View.GONE);
        zoomOutBtn.setVisibility(View.GONE);
        zoomInBtn.setVisibility(View.GONE);
        venueListBtn.setVisibility(View.VISIBLE);

        hideProgressbar();
    }

    public void showSearchSuggestions(List<Minivenue> minivenues){
        suggestionRecycler.setVisibility(View.VISIBLE);
        suggestionAdapter.setMinivenues(minivenues);
        suggestionAdapter.notifyDataSetChanged();

        locationBtn.setVisibility(View.GONE);
        zoomOutBtn.setVisibility(View.GONE);
        zoomInBtn.setVisibility(View.GONE);
    }


    @Override
    public void onItemClick(Category category) {
        suggestionRecycler.setVisibility(View.GONE);
        displayProgressbar();
        presenter.getVenuesWithCategory(category.getCategoryId());
    }

    @Override
    public void onItemClick(VenueEntity venue) {
        //go to venuelist fragment
    }

    @Override
    public void onItemClick(Minivenue minivenue) {
        //handle click on suggestion
    }

    private void displayProgressbar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar(){
        progressBar.setVisibility(View.GONE);
    }

}
