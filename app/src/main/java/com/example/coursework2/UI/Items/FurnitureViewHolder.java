package com.example.coursework2.UI.Items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework2.R;

public class FurnitureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Widgets
    TextView nameTV, priceTV, webTV, imageTV;
    ImageView itemImage;

    OnFurnitureListener onFurnitureListener;

    public FurnitureViewHolder(@NonNull View itemView, OnFurnitureListener onFurnitureListener) {
        super(itemView);

        // Binding
        nameTV = itemView.findViewById(R.id.furniture_name);
        priceTV = itemView.findViewById(R.id.furniture_price);
        webTV = itemView.findViewById(R.id.furniture_webUrl);
        imageTV = itemView.findViewById(R.id.furniture_imageUrl);
        itemImage = itemView.findViewById(R.id.furniture_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        onFurnitureListener.onFurnitureClick(getAdapterPosition());
    }
}
