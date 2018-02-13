package com.android.devolo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.devolo.ui.adapters.ImageFragmentAdapter;
import com.android.devolo.model.ListItemInfo;
import com.android.devolo.MainActivity;
import com.android.devolo.R;

import java.util.List;

/**
 * Created by elitsa on 9.2.2018 г..
 */

public class ImagesFragment extends Fragment {

    public static final String TAG = "ImagesFragment";

    List<ListItemInfo> listItemInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if (bundle != null){
            listItemInfo = bundle.getParcelableArrayList(MainActivity.KEY_SELECTED_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.images_fragment_layout, container, false);
        GridView gridView = view.findViewById(R.id.grid_view);
        ImageFragmentAdapter adapter = new ImageFragmentAdapter(getContext());
        adapter.setListItemInfo(listItemInfo);
        gridView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}
