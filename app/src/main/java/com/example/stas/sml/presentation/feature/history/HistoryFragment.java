package com.example.stas.sml.presentation.feature.history;


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

    @BindView(R.id.placesVisited)RecyclerView placesRecycler;


    //new dependency
    HistoryRecyclerAdapter historyRecyclerAdapter = new HistoryRecyclerAdapter(this);

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
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        placesRecycler.setLayoutManager(verticalLayoutManager);
        placesRecycler.setAdapter(historyRecyclerAdapter);
        presenter.getVenues();
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
                //to do smth
                Toast.makeText(getContext(), "to save", Toast.LENGTH_SHORT).show();
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
