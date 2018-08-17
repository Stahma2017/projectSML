package com.example.stas.sml.presentation.feature.querysubmit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.VenuesByQuerySubmitRecyclerAdapter;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_venues_by_query_submit, container, false);
        App.getInstance().addQueryVenuesComponent().injectVenuesByQuerySubmitFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        placesBySubmitAdapter = new VenuesByQuerySubmitRecyclerAdapter(getActivity());
        LinearLayoutManager suggestionsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        suggestionsBySubmitRecycler.setLayoutManager(suggestionsLayoutManager);
        suggestionsBySubmitRecycler.setAdapter(placesBySubmitAdapter);

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

    public void showPlacesByQuerySubmit(List<VenueEntity> venues){
        placesBySubmitAdapter.notifyDataSetChanged();
    }
}
