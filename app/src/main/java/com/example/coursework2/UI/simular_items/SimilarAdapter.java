package com.example.coursework2.UI.simular_items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursework2.R;
import com.example.coursework2.UI.saved_items.FurnitureViewHolder;
import com.example.coursework2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SimilarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Item> mSimilarItems;
    private List<Item> checkedItems = new ArrayList<>();
    private OnSimilarItemListener onSimilarItemListener;

    public List<Item> getCheckedItems() {
        return checkedItems;
    }

    public SimilarAdapter(OnSimilarItemListener onSimilarItemListener) {
        this.onSimilarItemListener = onSimilarItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.furniture_list_item, parent, false);
        return new SimilarViewHolder(view, onSimilarItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((SimilarViewHolder)holder).nameTV.setText(mSimilarItems.get(i).getName());
        ((SimilarViewHolder)holder).priceTV.setText(mSimilarItems.get(i).getPrice());
        ((SimilarViewHolder)holder).webTV.setText(mSimilarItems.get(i).getWebUrl());

        ((SimilarViewHolder)holder).setOnSimilarItemListener(new OnSimilarItemListener() {
            @Override
            public void onSimilarItemClick(View v, int pos) {
                CheckBox checkBox = (CheckBox)v;
                if (checkBox.isChecked()) {
                    checkedItems.add(mSimilarItems.get(pos));
                } else {
                    checkedItems.remove(mSimilarItems.get(pos));
                }
            }

            @Override
            public void onWebClick(int position) {

            }
        });

        // ImageView
        Glide.with(holder.itemView.getContext())
                .load(mSimilarItems.get(i).getImageUrl())
                .into(((SimilarViewHolder)holder).itemImage);
    }

    @Override
    public int getItemCount() {
        if (mSimilarItems != null) {
            return mSimilarItems.size();
        }
        return 0;
    }

    public void setmSimilarItems(List<Item> mSimilarItems) {
        this.mSimilarItems = mSimilarItems;
        notifyDataSetChanged();
    }
}
