package com.example.stas.sml.presentation.feature.save;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.presentation.feature.save.adapter.SaveRecyclerAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SaveFragment extends Fragment implements SaveContract.SaveView, SaveRecyclerAdapter.OnItemClickListener {

    private Unbinder unbinder;

    @Inject
    SavePresenter presenter;

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
    public void onItemClick(View itemView, VenueDb venuedb) {

    }

    @Override
    public void showError(String errorMessage) {

    }
}
