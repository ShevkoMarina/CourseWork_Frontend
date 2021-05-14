package com.example.coursework2.UI.category_items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursework2.R;
import com.example.coursework2.UI.saved_items_clicked.SavedItemsAdapter;
import com.example.coursework2.model.CategoryItem;

import java.util.List;

public class CategoryItemsAdapter  extends RecyclerView.Adapter<CategoryItemsAdapter.CategoryItemsViewHolder>{

    private List<CategoryItem> categoryItems;
    private OnCategoryListener listener;

    public CategoryItemsAdapter(List<CategoryItem> categoryItems, OnCategoryListener listener) {
        this.categoryItems = categoryItems;
        this.listener = listener;
    }

    public void setCategoryItems(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,
                parent, false);
        return new CategoryItemsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemsViewHolder holder, int i) {
        holder.categoryTV.setText(categoryItems.get(i).getCategory());
        Glide.with(holder.itemView.getContext()).load(categoryItems.get(i).getImageUrl()).into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        if (categoryItems != null) {
            return categoryItems.size();
        }
        return 0;
    }

    public class CategoryItemsViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        // Widgets
        ImageView categoryImage;
        TextView categoryTV;

        OnCategoryListener listener;

        public CategoryItemsViewHolder(@NonNull View itemView, OnCategoryListener listener) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.category_image);
            categoryTV = itemView.findViewById(R.id.category_name);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onCategoryItemClick(getAdapterPosition());
        }
    }

    public interface OnCategoryListener {
        void onCategoryItemClick(int position);
    }
}
