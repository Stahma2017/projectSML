package com.example.stas.sml.presentation.feature.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import com.example.stas.sml.App;
import com.example.stas.sml.CategoryRecyclerAdapter;
import com.example.stas.sml.VenuesByCategoryRecyclerAdapter;
import com.example.stas.sml.R;
import com.example.stas.sml.SearchSuggestionsRecyclerAdapter;
import com.example.stas.sml.VenuesByQuerySubmitRecyclerAdapter;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.map.map.MapsFragment;
import com.example.stas.sml.presentation.feature.map.querysubmit.VenuesByQuerySubmitFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements MapsContract.MapView, OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;


    //New dependency
    private VenuesByCategoryRecyclerAdapter placesAdapter;

    //New dependency
    private SearchSuggestionsRecyclerAdapter suggestionAdapter;

    @Inject
    MapsContract.Presenter presenter;
    @Inject
    ErrorHandler errorHandler;


    @BindView(R.id.bottomAppBar)BottomNavigationView bottomNavigation;
    @BindView(R.id.myTool)Toolbar toolbar;

    @BindView(R.id.categoryRecycler)RecyclerView categoryRecycler;
    @BindView(R.id.placesRecycler)RecyclerView placesRecycler;

    @BindView(R.id.suggestion_list)RecyclerView suggestionRecycler;

    @BindView(R.id.fragment_container)FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        App.getInstance().addMapsComponent().injectMainActivity(this);
        ButterKnife.bind(this);
        presenter.attachView(this);
        setUpMap();

        presenter.checkNetworkConnection();
        setSupportActionBar(toolbar);

        CategoryRecyclerAdapter categoryAdapter = new CategoryRecyclerAdapter(presenter);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecycler.setLayoutManager(horizontalLayoutManager);  // Layout менеджеры можешь закинуть через DI
        categoryRecycler.setAdapter(categoryAdapter);

        placesAdapter = new VenuesByCategoryRecyclerAdapter(presenter);
        LinearLayoutManager placesLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        placesRecycler.setLayoutManager(placesLayoutManager);
        placesRecycler.setAdapter(placesAdapter);

        suggestionAdapter = new SearchSuggestionsRecyclerAdapter(this);
        LinearLayoutManager suggestionManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        suggestionRecycler.setLayoutManager(suggestionManager);
        suggestionRecycler.setAdapter(suggestionAdapter);



        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {  //Через лямбду
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId()) {

                    case R.id.action_map:
                        fragmentTransaction.replace(R.id.fragment_container, new MapsFragment());
                        fragmentTransaction.commit();
                        break;
                }
                return false;
            }
        });


    }

    @Override
    public Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);   //SupressLint, если уверен, что пермишен точно будет к вызову этого метода
        return location;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, toolbar.getMenu());

        SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                suggestionRecycler.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new VenuesByQuerySubmitFragment());
                fragmentTransaction.commit();


                presenter.getVenuesByQuerySubmit(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                    if (s.length() >= 3){  //Debounce и RxBindings, чтобы у тебя этот метод постоянно не тригерился, когда чувак начинает вводить и стирать быстро
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
                toolbar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rectangle_14_edited));
                categoryRecycler.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.053984, 38.981784), 15.0f));
        mMap.setMyLocationEnabled(true); //SupressLint, если уверен, что пермишен точно будет к вызову этого метода
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void setUpMap() {
        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void setMarker(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions().
                position(latLng).
                title("Custom location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
        marker = mMap.addMarker(markerOptions);
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForMap() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForMap() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPlacesByCategory() {
        placesAdapter.notifyDataSetChanged();
        placesRecycler.setVisibility(View.VISIBLE);
     /*   locationBtn.setVisibility(View.GONE);
        zoomOutBtn.setVisibility(View.GONE);
        zoomInBtn.setVisibility(View.GONE);*/
    }

    @Override
    public void showPlacesByQuerySubmit(List<VenueEntity> venues){
     //   suggestionsBySubmitRecycler.setVisibility(View.VISIBLE);
        suggestionRecycler.setVisibility(View.GONE);
     //   placesBySubmitAdapter.setVenues(venues);
     //   placesBySubmitAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSearchSuggestions(List<Minivenue> minivenues){
        suggestionRecycler.setVisibility(View.VISIBLE);
        suggestionAdapter.setMinivenues(minivenues);
        suggestionAdapter.notifyDataSetChanged();
/*
        locationBtn.setVisibility(View.GONE);
        zoomOutBtn.setVisibility(View.GONE);
        zoomInBtn.setVisibility(View.GONE);*/
    }

}