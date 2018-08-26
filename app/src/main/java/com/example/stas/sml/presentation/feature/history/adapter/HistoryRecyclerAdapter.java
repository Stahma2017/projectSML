package com.example.stas.sml.presentation.feature.history.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.stas.sml.R;
import com.example.stas.sml.data.database.entity.VenueDb;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.HisoryViewHolder>{

    private List<VenueDb> venues = new ArrayList<>();

    public void setList(List<VenueDb> venues){
        if(venues != null){
            this.venues = venues;
        }
    }

    @NonNull
    @Override
    public HisoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HisoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HisoryViewHolder holder, int position) {
        holder.bind(venues.get(position));
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    class HisoryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imageVisited)ImageView photoVisited;
        @BindView(R.id.nameVisited)TextView nameVisited;
        @BindView(R.id.addressVisited)TextView addressVisited;
        @BindView(R.id.workStatusVisited)TextView workStatusVisited;

        public HisoryViewHolder(@android.support.annotation.NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(VenueDb venue){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(25));
            com.example.stas.sml.GlideApp.with(photoVisited)
                    .load(venue.imageUrl)
                    .apply(requestOptions)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.no_image_rounded)
                    .into(photoVisited);
            nameVisited.setText(venue.name);
            addressVisited.setText(venue.address);
            workStatusVisited.setText(venue.workStatus);
        }

    }
}
