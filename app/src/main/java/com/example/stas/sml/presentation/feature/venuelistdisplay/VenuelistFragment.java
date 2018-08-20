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

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class VenuelistFragment extends Fragment implements VenuelistContract.VenuelistView {


    private Unbinder unbinder;

    @Inject
    VenuelistPresenter presenter;

    public VenuelistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }
}
