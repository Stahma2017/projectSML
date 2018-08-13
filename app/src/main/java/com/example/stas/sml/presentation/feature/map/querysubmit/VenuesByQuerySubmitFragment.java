package com.example.stas.sml.presentation.feature.map.querysubmit;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.VenuesByQuerySubmitRecyclerAdapter;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.feature.map.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VenuesByQuerySubmitFragment extends Fragment {

    private Unbinder unbinder;

    //New dependency
    private VenuesByQuerySubmitRecyclerAdapter placesBySubmitAdapter;

    @Inject
    VenuesByQuerySubmitPresenter presenter;

    @BindView(R.id.suggesiontByQuerySubmitList)RecyclerView suggestionsBySubmitRecycler;


    public VenuesByQuerySubmitFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_venues_by_query_submit, container, false);
        App.getInstance().addQueryVenuesComponent().injectVenuesByQuerySubmitFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        placesBySubmitAdapter = new VenuesByQuerySubmitRecyclerAdapter(getActivity());
        LinearLayoutManager suggestionsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        suggestionsBySubmitRecycler.setLayoutManager(suggestionsLayoutManager);
        suggestionsBySubmitRecycler.setAdapter(placesBySubmitAdapter);

        presenter.getSmth();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    void showToast(List<Minivenue> minivenues) {
        String s = minivenues.get(0).getName();
        Toast.makeText(getActivity(),s, Toast.LENGTH_SHORT).show();
    }



    public void showPlacesByQuerySubmit(List<VenueEntity> venues){
        placesBySubmitAdapter.notifyDataSetChanged();
    }
}
