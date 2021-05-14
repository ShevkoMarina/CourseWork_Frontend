package com.example.coursework2.UI.saved_items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework2.R;
import com.example.coursework2.model.Item;

import java.util.List;

public class FurnitureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Widgets
    TextView nameTV, priceTV, webTV, imageTV;
    ImageView itemImage;

    private OnFurnitureListener onFurnitureListener;
    private FurnitureRecyclerView adapter;

    public FurnitureViewHolder(@NonNull View itemView, OnFurnitureListener onFurnitureListener, FurnitureRecyclerView adapter) {
        super(itemView);

        // Binding
        nameTV = itemView.findViewById(R.id.furniture_name);
        priceTV = itemView.findViewById(R.id.furniture_price);
        webTV = itemView.findViewById(R.id.furniture_webUrl);
        itemImage = itemView.findViewById(R.id.furniture_image);
        this.onFurnitureListener = onFurnitureListener;
        this.adapter = adapter;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onFurnitureListener != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                List<Item> savedItems = adapter.getmFurnitureItems();
                //savedItems.get(position).openUrl(this);
                adapter.notifyItemChanged(position);
            }
        }
    }
}
