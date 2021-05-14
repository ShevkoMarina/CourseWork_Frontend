package com.example.coursework2.UI.simular_items;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework2.R;

public class SimilarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView nameTV, priceTV, webTV;
    ImageView itemImage;
    CheckBox checkBox;

    private OnSimilarItemListener onSimilarItemListener;

    public SimilarViewHolder(@NonNull View itemView, OnSimilarItemListener onSimilarItemListener) {
        super(itemView);

        // Binding
        nameTV = itemView.findViewById(R.id.furniture_name);
        priceTV = itemView.findViewById(R.id.furniture_price);
        webTV = itemView.findViewById(R.id.furniture_webUrl);
        itemImage = itemView.findViewById(R.id.furniture_image);
        checkBox = itemView.findViewById(R.id.similar_check_box);

        checkBox.setOnClickListener(this);
    }

    public void setOnSimilarItemListener(OnSimilarItemListener onSimilarItemListener) {
        this.onSimilarItemListener = onSimilarItemListener;
    }

    @Override
    public void onClick(View v) {
        onSimilarItemListener.onSimilarItemClick(v, getAdapterPosition());
    }
}
