package com.example.stas.sml.presentation.feature.venuelistdisplay.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stas.sml.R;

import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.utils.UrlHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PreviousPlacesByCategoryAdapter extends RecyclerView.Adapter<PreviousPlacesByCategoryAdapter.PreviousPlacesViewHolder> {

    private List<VenueEntity> venues = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(VenueEntity venue);
    }

    public void setVenues(List<VenueEntity> venues) {
        this.venues = venues;
    }
    public void refreshList(){
        venues.clear();
    }

    public void addVenue(VenueEntity venue){
        venues.add(venue);
    }

    public PreviousPlacesByCategoryAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PreviousPlacesByCategoryAdapter.PreviousPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PreviousPlacesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prevplace_recylcer_item, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousPlacesViewHolder holder, int position) {
        holder.bind(venues.get(position));
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    class PreviousPlacesViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener onItemClickListener;

        @BindView(R.id.placeLogoPrev)CircleImageView logo;
        @BindView(R.id.placeNamePrev)TextView name;
        @BindView(R.id.placeRatingPrev)AppCompatRatingBar rating;
        @BindView(R.id.placeShortDescriptionPrev)TextView shortDescription;
        @BindView(R.id.placeAddressPrev)TextView address;
        @BindView(R.id.workStatusPrev)TextView workStatus;
        @BindView(R.id.shortDistancePrev)TextView distance;
        @BindView(R.id.workIndicatorPrev)ImageView workIndicator;

        public PreviousPlacesViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
        }

        void bind(VenueEntity venue) {

            com.example.stas.sml.GlideApp.with(logo)
                    .load(UrlHelper.getUrlToPhoto(venue.getBestPhoto().getPrefix(), venue.getBestPhoto().getSuffix()))
                    .placeholder(R.drawable.circle_no_image)
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
