package com.example.stas.sml.presentation.feature.venuelistdisplay;


import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.stas.sml.R;
import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.service.GpsTracker;

import java.security.Provider;


public class VenuelistFragment extends Fragment {


    public VenuelistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        return inflater.inflate(R.layout.fragment_venue_list, container, false);
    }

}
