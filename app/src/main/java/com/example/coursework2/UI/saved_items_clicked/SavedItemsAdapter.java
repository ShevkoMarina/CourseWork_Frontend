package com.example.coursework2.UI.saved_items_clicked;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursework2.R;
import com.example.coursework2.UI.recognition.RecognizedItemViewHolder;
import com.example.coursework2.UI.saved_items.FurnitureViewHolder;
import com.example.coursework2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SavedItemsAdapter  extends  RecyclerView.Adapter<SavedItemsAdapter.SavedItemsViewHolder>{

    private List<Item> savedItems;
    private OnSavedItemsListener listener;

    public SavedItemsAdapter(List<Item> savedItems, OnSavedItemsListener listener) {
        this.savedItems = savedItems;
        this.listener = listener;
    }

    public List<Item> getSavedItems() {
        return savedItems;
    }

    public void setSavedItems(List<Item> savedItems) {
        this.savedItems = savedItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_item,
                parent, false);
        return new SavedItemsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedItemsViewHolder holder, int i) {
        ((SavedItemsViewHolder)holder).nameTV.setText(savedItems.get(i).getName());
        ((SavedItemsViewHolder)holder).priceTV.setText(savedItems.get(i).getPrice());
     //   ((SavedItemsViewHolder)holder).webTV.setText(savedItems.get(i).getWebUrl());

        // ImageView
        Glide.with(holder.itemView.getContext())
                .load(savedItems.get(i).getImageUrl())
                .into(((SavedItemsViewHolder)holder).itemImage);
    }

    @Override
    public int getItemCount() {
        if (savedItems != null) {
            return savedItems.size();
        }
        return 0;
    }

    public class SavedItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Widgets
        TextView nameTV, priceTV, webTV;
        ImageView itemImage;

        // Listener
        OnSavedItemsListener listener;

        public SavedItemsViewHolder(@NonNull View itemView, OnSavedItemsListener listener) {
            super(itemView);

            // Binding
            nameTV = itemView.findViewById(R.id.saved_name);
            priceTV = itemView.findViewById(R.id.saved_price);
           // webTV = itemView.findViewById(R.id.furniture_webUrl);
            itemImage = itemView.findViewById(R.id.saved_image);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onSavedItemClick(getAdapterPosition());
        }
    }

    public interface OnSavedItemsListener {
        void onSavedItemClick(int position);
    }
}
