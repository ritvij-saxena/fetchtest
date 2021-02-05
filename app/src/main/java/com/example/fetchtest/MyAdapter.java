package com.example.fetchtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

class MyAdapter extends BaseExpandableListAdapter{
    Map<Integer, List<Items>> items;
    private Context context;
    private Object[] keys;

    public MyAdapter(Context context){
        this.context = context;
    }
    void setData(Map<Integer,List<Items>>items, Object[] keys){
        this.items = items;
        this.keys = keys;
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        //headers
        return keys == null ? 0 : keys.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return items.get(keys[i]).size();
    }

    @Override
    public Object getGroup(int i) {
        return keys[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return items.get(keys[i]).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String title = getGroup(i).toString();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_group_layout,null);
        }
        TextView groupTextView = view.findViewById(R.id.textViewListId);
        groupTextView.setText(title);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Items item = (Items) getChild(i,i1);
        String name = item.getName();
        String id = Integer.toString(item.getId());
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list_layout,null);
        }
        TextView nameTextView = view.findViewById(R.id.textViewName);
        TextView idTextView = view.findViewById(R.id.textViewId);
        nameTextView.setText(name);
        idTextView.setText(id);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}