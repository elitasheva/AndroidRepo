package com.android.devolo.ui.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.devolo.R;
import com.android.devolo.model.ListItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elitsa on 10.2.2018 г.
 */

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.ListItemViewHolder> {

    private List<ListItemInfo> listItemInfo;

    public ListFragmentAdapter() {
        listItemInfo = new ArrayList<>();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, viewGroup, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        ListItemInfo current = listItemInfo.get(position);
        holder.title.setText(current.getTitle());
        holder.userLogin.setText(current.getUsername());
        if (current.isSelected()){
            holder.itemView.setBackgroundColor(Color.GRAY);
        }else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return listItemInfo.size();
    }

    public void setListItemInfo(List<ListItemInfo> listItemInfo){
        this.listItemInfo = listItemInfo;
        notifyDataSetChanged();
    }

    public List<ListItemInfo> getListItemInfo() {
        return listItemInfo;
    }

    public List<ListItemInfo> getSelectedItems(){
        List<ListItemInfo> selectedItems = new ArrayList<>();
        for (ListItemInfo itemInfo : listItemInfo) {
            if (itemInfo.isSelected()){
                selectedItems.add(itemInfo);
            }
        }
        return selectedItems;
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView userLogin;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            userLogin = itemView.findViewById(R.id.user_login);
            itemView.setOnClickListener(this);
            itemView.setSelected(false);
        }

        @Override
        public void onClick(View v) {
            v.setSelected(!v.isSelected());
            ListItemInfo current = listItemInfo.get(getAdapterPosition());
            current.setSelected(v.isSelected());
            if (v.isSelected()){
                v.setBackgroundColor(Color.GRAY);
            }else {
                v.setBackgroundColor(Color.WHITE);
            }
        }
    }
}
