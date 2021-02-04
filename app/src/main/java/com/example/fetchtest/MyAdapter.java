package com.example.fetchtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {
    List<Items> items;
    Context context;

    private static final String TAG = "MyAdapter";

    void setListData(List<Items> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context = parent.getContext();
        return new MyAdapter.MyAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyAdapterViewHolder holder, int position) {
        Items item = items.get(position);
//        Log.d(TAG, "onBindViewHolder: "+item.getName()+ item.getId()+ item.getListId());
        if (item != null) {
            holder.id.setText(Integer.toString(item.getId()));
            holder.name.setText(item.getName());
            holder.listId.setText(Integer.toString(item.getListId()));

        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + items);
        return items == null ? 0 : items.size();
    }

    public static class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView name;
        public TextView listId;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.textViewId);
            listId = itemView.findViewById(R.id.textViewListId);
            name = itemView.findViewById(R.id.textViewName);
        }
    }
}
