package com.android.devolo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.devolo.R;
import com.android.devolo.model.ListItemInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elitsa on 10.2.2018 г.
 */

public class ImageFragmentAdapter extends BaseAdapter {

    private List<ListItemInfo> listItemInfo;
    private Context ctx;

    public ImageFragmentAdapter(Context ctx) {
        this.listItemInfo = new ArrayList<>();
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return listItemInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return listItemInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listItemInfo.get(position).hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        ImageView image;

        if (v == null){
            v = LayoutInflater.from(ctx)
                    .inflate(R.layout.grid_item_layout, parent, false);
        }

        image = v.findViewById(R.id.image);
        String url = listItemInfo.get(position).getImageUrl();
        Picasso.with(ctx).load(url).into(image);
        return image;
    }

    public void setListItemInfo(List<ListItemInfo> listItemInfo) {
        this.listItemInfo = listItemInfo;
        notifyDataSetChanged();
    }
}
