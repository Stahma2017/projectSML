package com.example.stas.sml.presentation.feature.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.presentation.feature.history.adapter.HistoryRecyclerAdapter;
import com.example.stas.sml.presentation.feature.main.MainActivity;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HistoryFragment extends Fragment implements HistoryContract.HistoryView, HistoryRecyclerAdapter.OnItemClickListener {

    private Unbinder unbinder;

    @Inject
    HistoryPresenter presenter;
    @Inject
    HistoryRecyclerAdapter historyRecyclerAdapter;
    @BindView(R.id.placesVisited)RecyclerView placesRecycler;
    @BindView(R.id.searchHistory)SearchView search;
    @BindView(R.id.titleHistory)TextView title;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        App.getInstance().addHistoryComponent(this, this).injectHistoryFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        placesRecycler.setLayoutManager(verticalLayoutManager);
        placesRecycler.setAdapter(historyRecyclerAdapter);
        presenter.getVenues();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                historyRecyclerAdapter.setFilter(newText);
                historyRecyclerAdapter.notifyDataSetChanged();
                return true;
            }
        });
        search.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                title.setVisibility(View.GONE);
            }
        });

        search.setOnCloseListener(() -> {
            title.setVisibility(View.VISIBLE);
            return false;
        });
        return view;
    }

    @Override
    public void onItemClick(View itemView, VenueDb venuedb) {
        switch (itemView.getId()){
            case R.id.toMapBtnVisited:
                MainActivity activity = (MainActivity) getActivity();
                activity.pointLocationOnMap(venuedb.latitude, venuedb.longitude, venuedb.name);
                break;
            case R.id.toSaveBtnVisited:
               presenter.saveVenue(venuedb.id, true);
                Toast.makeText(getContext(), venuedb.name + " saved",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        App.getInstance().clearHistoryComponent();
        presenter.detachView();
    }

    @Override
    public void showPlaces(List<VenueDb> venueDbs) {
        historyRecyclerAdapter.setList(venueDbs);
        historyRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMessage) {

    }
}
