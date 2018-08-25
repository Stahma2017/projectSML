package com.example.stas.sml.presentation.feature.venueselected.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.stas.sml.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.GalleryViewHolder>{

    private List<String> photoUrls = new ArrayList<>();

    public void setList(List<String> photoUrls){
        if (photoUrls != null) {
            this.photoUrls = photoUrls;
        }
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.bind(photoUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return photoUrls.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.venuePhoto)ImageView venuePhoto;


      public GalleryViewHolder(@NonNull View itemView) {
          super(itemView);
          ButterKnife.bind(this, itemView);
      }

      void bind(String url){
          RequestOptions requestOptions = new RequestOptions();
          requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
          com.example.stas.sml.GlideApp.with(venuePhoto)
                  .load(url)
                  .apply(requestOptions)
                  .placeholder(R.drawable.no_image)
                  .into(venuePhoto);
      }
  }





}
