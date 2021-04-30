package com.example.coursework2.UI.recognition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursework2.R;
import com.example.coursework2.model.RecognizedItem;

import java.util.List;

public class RecognizedItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RecognizedItem> mItems;
    private OnRecognitionListener onRecognitionListener;

    public RecognizedItemsAdapter(OnRecognitionListener listener) {
        this.onRecognitionListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recognized_item,
                parent, false);
        return new RecognizedItemViewHolder(view, onRecognitionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((RecognizedItemViewHolder)holder).categoryTV.setText(mItems.get(i).getName());

        Glide.with(((RecognizedItemViewHolder) holder)
                .itemPhoto.getContext())
                .load(mItems.get(i).getUrl())
                .into(((RecognizedItemViewHolder)holder).itemPhoto);
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    public void setRecognizedItems(List<RecognizedItem> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();;
    }
}
