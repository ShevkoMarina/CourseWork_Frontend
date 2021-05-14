package com.example.coursework2.UI.saved_items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursework2.R;
import com.example.coursework2.model.Item;

import java.util.List;

public class FurnitureRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> mFurnitureItems;
    private OnFurnitureListener onFurnitureListener;

    public FurnitureRecyclerView(OnFurnitureListener listener) {
        this.onFurnitureListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.furniture_list_item,
                parent, false);
        return new FurnitureViewHolder(view, onFurnitureListener, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((FurnitureViewHolder)holder).nameTV.setText(mFurnitureItems.get(i).getName());
        ((FurnitureViewHolder)holder).priceTV.setText(mFurnitureItems.get(i).getPrice());
        ((FurnitureViewHolder)holder).webTV.setText(mFurnitureItems.get(i).getWebUrl());

        // ImageView
        Glide.with(holder.itemView.getContext())
                .load(mFurnitureItems.get(i).getImageUrl())
                .into(((FurnitureViewHolder)holder).itemImage);
    }

    @Override
    public int getItemCount() {
        if (mFurnitureItems != null) {
            return mFurnitureItems.size();
        }
        return 0;
    }

    public void setmFurnitureItems(List<Item> mFurnitureItems) {
        this.mFurnitureItems = mFurnitureItems;
        notifyDataSetChanged();
    }

    public List<Item> getmFurnitureItems() {
        return mFurnitureItems;
    }
}
