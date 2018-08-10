package com.example.stas.sml;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;

import java.util.ArrayList;
import java.util.List;

public class VenuesByQuerySubmitRecyclerAdapter extends RecyclerView.Adapter<VenuesByQuerySubmitRecyclerAdapter.SearchSuggestionsBySubmitViewHolder> {

    private List<VenueEntity> venues = new ArrayList<>();
    private Context context;

    public VenuesByQuerySubmitRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setVenues(List<VenueEntity> venues) {
        this.venues = venues;
    }

    @NonNull
    @Override
    public VenuesByQuerySubmitRecyclerAdapter.SearchSuggestionsBySubmitViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.suggestion_by_query_submit_recycler_item, null);
        VenuesByQuerySubmitRecyclerAdapter.SearchSuggestionsBySubmitViewHolder viewHolder = new VenuesByQuerySubmitRecyclerAdapter.SearchSuggestionsBySubmitViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VenuesByQuerySubmitRecyclerAdapter.SearchSuggestionsBySubmitViewHolder searchSuggestionsBySubmitViewHolder, int i) {
       searchSuggestionsBySubmitViewHolder.name.setText(venues.get(i).getName());
       searchSuggestionsBySubmitViewHolder.shortDescription.setText(venues.get(i).getDescription());
       searchSuggestionsBySubmitViewHolder.address.setText(venues.get(i).getLocation().getAddress());
       searchSuggestionsBySubmitViewHolder.workStatus.setText(venues.get(i).getHours().getStatus());
       searchSuggestionsBySubmitViewHolder.distance.setText(venues.get(i).getDistance().toString());
      /* if(venues.get(i).getHours().getOpen()) {
           //set workindicator
       }*/
        // set logo
        // set rating
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public class SearchSuggestionsBySubmitViewHolder extends RecyclerView.ViewHolder{

        private ImageView logo;
        private TextView name;
        private AppCompatRatingBar rating;
        private TextView shortDescription;
        private TextView address;
        private TextView workStatus;
        private TextView distance;
        private ImageView workIndicator;


        public SearchSuggestionsBySubmitViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.placeLogoFromSubmitSuggestion);
            name = itemView.findViewById(R.id.placeNameFromSubmitSuggestion);
            rating = itemView.findViewById(R.id.placeRatingFromSubmitSuggestion);
            shortDescription = itemView.findViewById(R.id.placeShortDescriptionFromSubmitSuggestion);
            address = itemView.findViewById(R.id.placeAddressFromSubmitSuggestion);
            workStatus = itemView.findViewById(R.id.workStatusFromSubmitSuggestion);
            distance = itemView.findViewById(R.id.shortDistanceFromSubmitSuggestion);
            workIndicator = itemView.findViewById(R.id.workIndicatorFromSubmitSuggestion);
        }

        }
    }


