package com.example.stas.sml.presentation.feature.map;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;


import com.example.stas.sml.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesActivity extends AppCompatActivity {

    @BindView(R.id.NavView)
    BottomNavigationView Navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
    }
}
