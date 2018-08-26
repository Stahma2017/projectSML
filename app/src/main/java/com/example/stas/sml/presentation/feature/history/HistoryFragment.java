package com.example.stas.sml.presentation.feature.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stas.sml.App;
import com.example.stas.sml.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HistoryFragment extends Fragment implements HistoryContract.HistoryView {

    private Unbinder unbinder;

    @Inject
    HistoryPresenter presenter;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        App.getInstance().addHistoryComponent(this).injectHistoryFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        App.getInstance().clearHistoryComponent();
        presenter.detachView();
    }

    @Override
    public void showError(String errorMessage) {

    }
}
