package com.example.stas.sml.presentation.feature.map.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stas.sml.Category;
import com.example.stas.sml.R;
import com.example.stas.sml.utils.CategoryList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    private List<Category> categories = CategoryList.getInstance().getCategoryList();
    private OnItemClickListener onItemClickListener;



    public CategoryRecyclerAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    public void refreshList(){
        for (Category category:categories) {
            category.setEnabled(false);
        }
    }

    public int getEnabledCategory(){
        for (Category category:categories
             ) {
            if (category.isEnabled()){
                return categories.indexOf(category);
            }
        }
        return -1;
    }

    public String getEnabledCategoryId(){
        for (Category category:categories
                ) {
            if (category.isEnabled()){
                return category.getCategoryId();
            }
        }
        return null;
    }

    public void setEnabledCategory(int index){
        if (index == -1){
            refreshList();
        }else{
            Category category =  categories.get(index);
            category.setEnabled(true);
            categories.set(index, category);
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new CategoryViewHolder(LayoutInflater.from(parent.getContext())
       .inflate(R.layout.category_recycler_item, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
      holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener onItemClickListener;

        @BindView(R.id.categoryImage)
        ImageView categoryIcon;

        @BindView(R.id.categoryName)
        TextView categoryName;


        CategoryViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.onItemClickListener = onItemClickListener;
        }

        void bind(Category category) {
          if (category.isEnabled()){
              categoryIcon.setImageResource(category.getCategoryImageEnabled());
          } else {
              categoryIcon.setImageResource(category.getCategoryImage());
          }
            categoryName.setText(category.getCategoryName());
            itemView.setOnClickListener(itemView -> onItemClickListener.onItemClick(category));

        }

    }

}
