package com.example.stas.sml.presentation.feature.save.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.stas.sml.R;
import com.example.stas.sml.data.database.entity.VenueDb;
import com.example.stas.sml.presentation.feature.history.adapter.HistoryRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaveRecyclerAdapter extends RecyclerView.Adapter<SaveRecyclerAdapter.SaveViewHolder> {

    private List<VenueDb> venues = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setList(List<VenueDb> venues){
        if(venues != null){
            this.venues = venues;
        }
    }

    public SaveRecyclerAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, VenueDb venuedb);
    }

    @NonNull
    @Override
    public SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SaveViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_recycler_item, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveViewHolder holder, int position) {
        holder.bind(venues.get(position));
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    class SaveViewHolder extends RecyclerView.ViewHolder{

        private OnItemClickListener onItemClickListener;

        @BindView(R.id.imageSaved)ImageView photoSaved;
        @BindView(R.id.nameSaved)TextView nameSaved;
        @BindView(R.id.addressSaved)TextView addressSaved;
        @BindView(R.id.workStatusSaved)TextView workStatusSaved;
        @BindView(R.id.toMapBtnSaved)Button toMapBtn;
        @BindView(R.id.toSaveBtnSaved)Button toSaveBtn;

        public SaveViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
        }

        void bind(VenueDb venue){

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(25));
            com.example.stas.sml.GlideApp.with(photoSaved)
                    .load(venue.imageUrl)
                    .apply(requestOptions)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.no_image_rounded)
                    .into(photoSaved);
            nameSaved.setText(venue.name);
            addressSaved.setText(venue.address);
            workStatusSaved.setText(venue.workStatus);
            toMapBtn.setOnClickListener(itemView -> onItemClickListener.onItemClick(itemView, venue));
            toSaveBtn.setOnClickListener(itemView -> onItemClickListener.onItemClick(itemView, venue)); 

        }
    }
}
