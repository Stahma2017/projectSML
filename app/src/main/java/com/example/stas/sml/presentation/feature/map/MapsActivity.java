package com.example.stas.sml.presentation.feature.map;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import com.example.stas.sml.App;
import com.example.stas.sml.Category;
import com.example.stas.sml.CategoryRecyclerAdapter;
import com.example.stas.sml.R;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@RuntimePermissions
public class MapsActivity extends AppCompatActivity implements MapsContract.MapView, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    private Marker marker;
    private BottomSheetBehavior bottomSheetBehavior;
    private List<Category> categoryList = new ArrayList<>();

    //New dependency
    private CategoryRecyclerAdapter categoryAdapter;


    @Inject
    MapsContract.Presenter presenter;
    @Inject
    ErrorHandler errorHandler;

    @BindView(R.id.bottomAppBar)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.myTool) Toolbar toolbar;
    @BindView(R.id.bottom_sheet) View bottomSheet;
    @BindView(R.id.categoryRecycler)
    RecyclerView categoryRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        App.getInstance().addMapsComponent().injectMapsActivity(this);
        ButterKnife.bind(this);
        presenter.attachView(this);
        setUpMap();
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        presenter.checkNetworkConnection();
        setSupportActionBar(toolbar);
        categoryAdapter = new CategoryRecyclerAdapter(categoryList, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecycler.setLayoutManager(horizontalLayoutManager);
        categoryRecycler.setAdapter(categoryAdapter);
        populateCategoryList();

    }

    void populateCategoryList(){
        Category all = new Category(R.drawable.all_category, "Все");
        Category drink = new Category(R.drawable.drink_category, "Выпить");
        Category read = new Category(R.drawable.read_category, "Почитать");
        Category play = new Category(R.drawable.play_category, "Поиграть");
        Category watch = new Category(R.drawable.watch_category, "Поигарть");
        categoryList.add(all);
        categoryList.add(drink);
        categoryList.add(read);
        categoryList.add(play);
        categoryList.add(watch);
        categoryList.add(drink);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, toolbar.getMenu());

        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                toolbar.setBackgroundColor(Color.WHITE);
                categoryRecycler.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                toolbar.setBackground(ContextCompat.getDrawable(MapsActivity.this, R.drawable.rectangle_14_edited));
                categoryRecycler.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        setMarker(latLng);
        MapsActivityPermissionsDispatcher.setMarkerWithPermissionCheck(this, latLng);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        setMarker(latLng);
        MapsActivityPermissionsDispatcher.setMarkerWithPermissionCheck(this, latLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        presenter.loadVenueId(latLng);
    }

    public void showSlider(List<String> urls) {
      /*  TextSliderView textSliderView = new TextSliderView(this);
        for(String url : urls){
            textSliderView
                    .image(url)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            parallax.addSlider(textSliderView);
        }*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.053984, 38.981784), 15.0f));
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void setUpMap(){
        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void setMarker(LatLng latLng){
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Custom location");
        marker = mMap.addMarker(markerOptions);
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForMap(){
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForMap(){
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
}