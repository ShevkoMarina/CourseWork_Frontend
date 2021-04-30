package com.example.coursework2.UI.recognition;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework2.R;

public class RecognizedItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView categoryTV;
    public ImageView itemPhoto;

    OnRecognitionListener onRecognitionListener;
    public RecognizedItemViewHolder(@NonNull View itemView, OnRecognitionListener onRecognitionListener) {
        super(itemView);

        categoryTV = itemView.findViewById(R.id.recognized_category);
        itemPhoto = itemView.findViewById(R.id.recognizedImage);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRecognitionListener.onRecognizedItemClick(getAdapterPosition());
    }
}
