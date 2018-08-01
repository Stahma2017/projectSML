package com.example.stas.sml;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PlacesRecyclerAdapter extends RecyclerView.Adapter<PlacesRecyclerAdapter.PlacesViewHolder> {
    private List<String> categoryList;
    Context context;

    public PlacesRecyclerAdapter(List<String> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View placesView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.places_recycler_item, viewGroup, false);
        PlacesRecyclerAdapter.PlacesViewHolder pvh = new PlacesRecyclerAdapter.PlacesViewHolder(placesView);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }





    public class PlacesViewHolder extends RecyclerView.ViewHolder {


        public PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
        }


    }
}
