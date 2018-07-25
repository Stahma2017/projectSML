package com.example.stas.sml.presentation.feature.map;


import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.stas.sml.App;
import com.example.stas.sml.MapsFragment;
import com.example.stas.sml.R;


public class MapsActivity extends AppCompatActivity{


    /*@Inject
    MapsContract.Presenter presenter;
    @Inject
    ErrorHandler errorHandler;*/

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       //  App.getInstance().addMapsComponent().injectMapsActivity(this);
        ButterKnife.bind(this);

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_search:
                                //do search
                                break;
                            case R.id.action_places:
                                //do places
                                break;
                            case R.id.action_map:
                                MapsFragment mapFragment = new MapsFragment();
                                FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                transaction.replace(R.id.fragment_container, mapFragment);
                                transaction.commit();

                                Log.d("map", "map itme clicked");
                                break;
                        }
                        return true;
                    }
                });

    }

   /* @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }*/
}