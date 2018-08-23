package com.example.stas.sml.presentation.feature.map.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stas.sml.R;
import com.example.stas.sml.data.model.venuesuggestion.Minivenue;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchSuggestionsRecyclerAdapter extends RecyclerView.Adapter<SearchSuggestionsRecyclerAdapter.SearchSuggestionViewHolder> {
    private List<Minivenue> minivenues = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setMinivenues(List<Minivenue> minivenues) {
        this.minivenues = minivenues;
    }

    public SearchSuggestionsRecyclerAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Minivenue minivenue);
    }

    @NonNull
    @Override
    public SearchSuggestionsRecyclerAdapter.SearchSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SearchSuggestionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_suggestion_recycler_item, null), onItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestionViewHolder holder, int position) {
        holder.bind(minivenues.get(position));
    }

    @Override
    public int getItemCount() {
        return minivenues.size();
    }

    public class SearchSuggestionViewHolder extends RecyclerView.ViewHolder  {
        private OnItemClickListener onItemClickListener;
        @BindView(R.id.searchSuggestionText)TextView textSuggestion;


        public SearchSuggestionViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
        }

        void bind(Minivenue minivenue) {
            textSuggestion.setText(minivenue.getName());
             itemView.setOnClickListener(itemView -> onItemClickListener.onItemClick(minivenue));
        }
    }

}
