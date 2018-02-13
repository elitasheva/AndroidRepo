package com.android.devolo;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.devolo.model.ListItemInfo;
import com.android.devolo.ui.ImagesFragment;
import com.android.devolo.ui.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.OnShowImagesClicked {

    public static final String KEY_SELECTED_DATA = "selected_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadListFragment();
    }

    @Override
    public void onShowImagesSelected(List<ListItemInfo> selectedItems) {
        ImagesFragment imagesFragment = new ImagesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_SELECTED_DATA, (ArrayList<? extends Parcelable>) selectedItems);
        imagesFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, imagesFragment, ImagesFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 2) {
            fm.popBackStack();
        } else {
            finish();
        }
    }

    private void loadListFragment() {
        ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(ListFragment.TAG);
        if (listFragment == null){
            listFragment = new ListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, listFragment, ListFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(listFragment)
                    .commit();
        }
    }
}
