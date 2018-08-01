package com.example.stas.sml;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    Context context;

    public CategoryRecyclerAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View categoryView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_recycler_item, viewGroup, false);
        CategoryViewHolder cvh = new CategoryViewHolder(categoryView);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int i) {
        holder.imageView.setImageResource(categoryList.get(i).getCategoryImage());
        holder.txtview.setText(categoryList.get(i).getCategoryName());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView txtview;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImage);
            txtview = itemView.findViewById(R.id.categoryName);
        }

        @Override
        public void onClick(View view) {


        }
    }
}
