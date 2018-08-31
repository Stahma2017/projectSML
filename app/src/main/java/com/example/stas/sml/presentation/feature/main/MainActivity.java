package com.example.stas.sml.presentation.feature.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.presentation.feature.history.HistoryFragment;
import com.example.stas.sml.presentation.feature.save.SaveFragment;
import com.example.stas.sml.presentation.feature.venueselected.VenueSelectedFragment;
import com.example.stas.sml.presentation.base.ErrorHandler;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.venuelistdisplay.VenuelistFragment;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    @Inject
    ActivityContract.Presenter presenter;
    @Inject
    ErrorHandler errorHandler;
    @Inject
    MapsFragment mapsFragment;
    @Inject
    VenuelistFragment venuelistFragment;
    @Inject
    VenueSelectedFragment venueSelectedFragment;
    @Inject
    HistoryFragment historyFragment;
    @Inject
    SaveFragment saveFragment;
    @BindView(R.id.bottomContainer)FrameLayout bottomContainer;
    @BindView(R.id.bottomAppBar)BottomNavigationView bottomNavigation;
    @BindView(R.id.fragment_container)FrameLayout fragmentContainer;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        App.getInstance().addMapsComponent().injectMainActivity(this);
        unbinder = ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.checkNetworkConnection();
        bottomNavigation.setSelectedItemId(R.id.action_map);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, mapsFragment);
        ft.commit();

        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_map:
                    menuItem.setChecked(true);
                    displayMapsFragment();
                    break;
                case R.id.action_account:
                    menuItem.setChecked(true);
                    displaySaveFragment();
                    break;
                case R.id.action_places:
                    menuItem.setChecked(true);
                    displayHistoryFragment();
            }
            return false;
        });
        Log.d("LIFE", "OnCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().clearMapsComponent();
        App.getInstance().clearAllComponents();
        presenter.detachView();
        unbinder.unbind();
        Log.d("LIFE", "OnDestroy");
    }
    public void displayMapsFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(mapsFragment);
        ft.remove(venuelistFragment);
        ft.remove(venueSelectedFragment);
        ft.remove(historyFragment);
        ft.remove(saveFragment);
        ft.commit();
    }

    public void displayHistoryFragment(){
        if (!historyFragment.isAdded()){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(venuelistFragment);
            ft.remove(venueSelectedFragment);
            ft.remove(saveFragment);
            ft.add(R.id.fragment_container, historyFragment);
            ft.commit();
        }
    }

    public void displaySaveFragment(){
        if(!saveFragment.isAdded()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(venuelistFragment);
            ft.remove(venueSelectedFragment);
            ft.remove(historyFragment);
            ft.add(R.id.fragment_container, saveFragment);
            ft.commit();
        }

    }

    public void displayVenuelistFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new VenuelistFragment());
        ft.hide(mapsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void displayVenueSelectedFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, venueSelectedFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void pointLocationOnMap(double langtitude, double longtitude, String name){
        mapsFragment.pointLocation(langtitude, longtitude, name);
        displayMapsFragment();
        bottomNavigation.setSelectedItemId(R.id.action_map);
    }

    public void hideToolbar(){
        bottomContainer.setVisibility(View.GONE);
    }

    public void showToolbar(){
        bottomContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

}