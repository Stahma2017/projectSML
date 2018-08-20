package com.example.stas.sml;
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
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenuesByCategoryRecyclerAdapter extends RecyclerView.Adapter<VenuesByCategoryRecyclerAdapter.SuggestionViewHolder> {

    private List<VenueEntity> venues = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setVenues(List<VenueEntity> venues) {
        this.venues = venues;
    }

    public void setList(List<VenueEntity> venues){
        this.venues = venues;
    }

    public void clearList(){
        venues.clear();
    }

    public VenuesByCategoryRecyclerAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(VenueEntity venue);
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SuggestionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion_recycler_item, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        holder.bind(venues.get(position));
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    class SuggestionViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener onItemClickListener;

        @BindView(R.id.placeLogo)ImageView logo;
        @BindView(R.id.placeName)TextView name;
        @BindView(R.id.placeRating)AppCompatRatingBar rating;
        @BindView(R.id.placeShortDescription)TextView shortDescription;
        @BindView(R.id.placeAddress)TextView address;
        @BindView(R.id.workStatus)TextView workStatus;
        @BindView(R.id.shortDistance)TextView distance;
        @BindView(R.id.workIndicator)ImageView workIndicator;

        SuggestionViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
        }

        void bind(VenueEntity venue) {

            com.example.stas.sml.GlideApp.with(logo)
                    .load(venue.getPage().getPageInfo().getBanner())
                    .placeholder(R.drawable.ic_image_placeholder_24dp)
                    .into(logo);

            name.setText(venue.getName());
            rating.setRating((float)(venue.getRating()/2));
            shortDescription.setText(venue.getDescription());
            address.setText(venue.getLocation().getAddress());
            workStatus.setText(venue.getHours().getStatus());
            distance.setText(String.format(Locale.US,"%.1f км", ((double)venue.getDistance())/1000));
            if (venue.getHours().getOpen()){
                workIndicator.setImageResource(R.drawable.work_indicator);
            }

            itemView.setOnClickListener(itemView -> onItemClickListener.onItemClick(venue));
        }
    }
}
