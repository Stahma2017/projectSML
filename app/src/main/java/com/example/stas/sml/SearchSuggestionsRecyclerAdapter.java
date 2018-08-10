package com.example.stas.sml;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stas.sml.data.model.venuesuggestion.Minivenue;

import java.util.ArrayList;
import java.util.List;

public class SearchSuggestionsRecyclerAdapter extends RecyclerView.Adapter<SearchSuggestionsRecyclerAdapter.SearchSuggestionViewHolder> {
    private List<Minivenue> minivenues = new ArrayList<>();
    private Context context;

    public void setMinivenues(List<Minivenue> minivenues) {
        this.minivenues = minivenues;
    }

    public SearchSuggestionsRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SearchSuggestionsRecyclerAdapter.SearchSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_suggestion_recycler_item, null);
        SearchSuggestionViewHolder viewHolder = new SearchSuggestionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestionsRecyclerAdapter.SearchSuggestionViewHolder searchSuggestionViewHolder, int i) {
        searchSuggestionViewHolder.textView.setText(minivenues.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return minivenues.size();
    }

    public class SearchSuggestionViewHolder extends RecyclerView.ViewHolder  {

        private TextView textView;

        public SearchSuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.searchSuggestionText);
        }
    }
}
