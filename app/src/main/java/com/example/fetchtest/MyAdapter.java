package com.example.fetchtest;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {
//    List<Items> items;
    Context context;
    Map<Integer,List<Items>> items;
    List<Boolean> expandState = new ArrayList<>();
    public MyAdapter(Context context){
        this.context = context;
    }

    private static final String TAG = "MyAdapter";

    void setData(Map<Integer,List<Items>> items) {
        this.items = items;
        setCardExpandStates(items.keySet().toArray());
        notifyDataSetChanged();
    }

    void setCardExpandStates(Object[] arr){
        for(int i=0;i<arr.length;i++){
            expandState.add(false);
        }
    }

    @NonNull
    @Override
    public MyAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapter.MyAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.MyAdapterViewHolder holder, final int position) {
        final Object[] keys = items.keySet().toArray();
        holder.listId.setText(keys[position].toString());

        holder.expandButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                myClick(position,holder,keys);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                myClick(position,holder,keys);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void myClick(int position, MyAdapterViewHolder holder, Object[] keys){
        expandState.set(position,!expandState.get(position));
        holder.expandButton.setRotation(expandState.get(position)? 180f: 0f);
    }


    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: " + items);
        return items == null ? 0 : items.size();
    }

    public static class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView listId;
        ImageButton expandButton;
        CardView cardView;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            listId = itemView.findViewById(R.id.textViewListId);
            expandButton = itemView.findViewById(R.id.imageButtonExpand);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
