package com.example.stas.sml;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.stas.sml.presentation.feature.map.MapsContract;

import java.util.Map;

public class CategorySuggestionRecyclerAdapter extends RecyclerView.Adapter<CategorySuggestionRecyclerAdapter.SuggestionViewHolder> {

    private final MapsContract.Presenter presenter;

    public CategorySuggestionRecyclerAdapter(MapsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategorySuggestionRecyclerAdapter.SuggestionViewHolder(presenter, LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySuggestionRecyclerAdapter.SuggestionViewHolder holder, int position) {
        presenter.onBindSuggestionRowViewAtPosition(position, holder);

    }

    @Override
    public int getItemCount() {
        return presenter.getSuggestionRowCount();
    }


    public class SuggestionViewHolder extends RecyclerView.ViewHolder implements MapsContract.CategorySuggestionRowView {
        ImageView logo;
        TextView name;
        AppCompatRatingBar rating;
        TextView shortDescription;
        TextView address;
        TextView workStatus;
        TextView distance;
        ImageView workIndicator;


        public SuggestionViewHolder(MapsContract.Presenter presenter, @NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.placeLogo);
            name = itemView.findViewById(R.id.placeName);
            rating = itemView.findViewById(R.id.placeRating);
            shortDescription = itemView.findViewById(R.id.placeShortDescription);
            address = itemView.findViewById(R.id.placeAddress);
            workStatus = itemView.findViewById(R.id.workStatus);
            distance = itemView.findViewById(R.id.shortDistance);
            workIndicator = itemView.findViewById(R.id.workIndicator);
        }

        @Override
        public void setLogo(String url) {
            com.example.stas.sml.GlideApp.with(logo)
                    .load(url)
                    .placeholder(R.drawable.ic_image_placeholder_24dp)
                    .into(logo);
        }

        @Override
        public void setWorkIndicator(boolean flag){
            if (flag){
                workIndicator.setImageResource(R.drawable.work_indicator);
            }
        }

         @Override
         public void setName(String name){
            this.name.setText(name);
         }

         @Override
         public void setDescription(String description){
            shortDescription.setText(description);
         }
         @Override
         public void setAddress(String address){
             this.address.setText(address);

         }
         @Override
         public void setWorkStatus(String workStatus){
             this.workStatus.setText(workStatus);

         }
         @Override
         public void setDistance(String distance){
             this.distance.setText(distance);
         }

        @Override
        public void setRating(float rating) {
            this.rating.setRating(rating);
    }

}
}
