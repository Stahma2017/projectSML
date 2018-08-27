package com.example.stas.sml.presentation.feature.save;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import com.example.stas.sml.presentation.feature.save.adapter.SaveRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SaveFragment extends Fragment implements SaveContract.SaveView, SaveRecyclerAdapter.OnItemClickListener {

    private Unbinder unbinder;

    @Inject
    SavePresenter presenter;
    @Inject
    SaveRecyclerAdapter saveRecyclerAdapter;
    @BindView(R.id.placesSave)RecyclerView placesRecycler;

    public SaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save, container, false);
        unbinder = ButterKnife.bind(this, view);
        App.getInstance().addSaveComponent(this, this).injectSaveFragment(this);
        presenter.attachView(this);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        placesRecycler.setLayoutManager(verticalLayoutManager);
        placesRecycler.setAdapter(saveRecyclerAdapter);
        presenter.getVenues();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        App.getInstance().clearSaveComponent();
        presenter.detachView();
    }

    @Override
    public void showPlaces(List<VenueDb> venueDbs) {
        saveRecyclerAdapter.setList(venueDbs);
        saveRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View itemView, VenueDb venuedb) {
        switch (itemView.getId()) {
            case R.id.toMapBtnSaved:
                MainActivity activity = (MainActivity) getActivity();
                activity.pointLocationOnMap(venuedb.latitude, venuedb.longitude, venuedb.name);
                break;
            case R.id.toSaveBtnSaved:
                presenter.unsaveVenue(venuedb.id, false);
                break;
        }
    }

    @Override
    public void showError(String errorMessage) {

    }
}
