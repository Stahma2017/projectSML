package com.example.stas.sml;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stas.sml.presentation.feature.map.MapsContract;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    /*private List<Category> categoryList;
    Context context;*/

    private final MapsContract.Presenter presenter;

    public CategoryRecyclerAdapter(MapsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new CategoryViewHolder(presenter, LayoutInflater.from(parent.getContext())
       .inflate(R.layout.category_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
      presenter.onBindCategoryRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getCategoryRowCount();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements MapsContract.CategoryRowView{
        ImageView categoryIcon;
        TextView categoryName;

        public CategoryViewHolder(MapsContract.Presenter presenter, @NonNull View itemView) {
            super(itemView);

            categoryIcon = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);

            itemView.setOnClickListener(view -> presenter.getVenuesWithCategory(CategoryViewHolder.this.getAdapterPosition()));
        }

        @Override
        public void setIcon(int icon) {
           categoryIcon.setImageResource(icon);
        }

        @Override
        public void setName(String name) {
           categoryName.setText(name);
        }

    }

}
