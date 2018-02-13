package com.android.devolo.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.devolo.utils.Constants;
import com.android.devolo.ui.adapters.ListFragmentAdapter;
import com.android.devolo.model.ListItemInfo;
import com.android.devolo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by elitsa on 9.2.2018 г..
 */

public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    public static final String TAG = "ListFragment";

    private static final int LOADER_ID = 123;

    private ListFragmentAdapter adapter;

    private RecyclerView recyclerView;

    private TextView errorMessage;

    private OnShowImagesClicked listener;

    public interface OnShowImagesClicked{
        void onShowImagesSelected(List<ListItemInfo> selectedItems);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnShowImagesClicked) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnShowImagesClicked) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);

        recyclerView = view.findViewById(R.id.list);
        adapter = new ListFragmentAdapter();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        errorMessage = view.findViewById(R.id.error_message);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.list_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show:
                List<ListItemInfo> selectedItems = adapter.getSelectedItems();
                if (selectedItems.size() == 0){
                    Toast.makeText(getContext(), R.string.nothing_selected, Toast.LENGTH_SHORT).show();
                }else {
                    if (listener != null) {
                        listener.onShowImagesSelected(selectedItems);
                    }
                }
                break;
            case R.id.select_all:
                selectAll(adapter.getListItemInfo());
                adapter.notifyDataSetChanged();
                break;
            case R.id.clear_all:
                clearAll(adapter.getListItemInfo());
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> loader = new InfoLoader(getContext());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data == null){
            recyclerView.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            errorMessage.setVisibility(View.GONE);
            try {
                List<ListItemInfo> listItemInfo = parseJson(data);
                adapter.setListItemInfo(listItemInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    private List<ListItemInfo> parseJson(String data) throws JSONException {
        ArrayList<ListItemInfo> result =  new ArrayList<>();
        JSONArray mainArray = new JSONArray(data);
        for (int i = 0; i < mainArray.length(); i++) {
            JSONObject current = (JSONObject) mainArray.get(i);
            String title = current.getString(Constants.TITLE);

            JSONObject inner = current.getJSONObject(Constants.USER);
            String username = inner.getString(Constants.USER_LOGIN);
            String imageUrl = inner.getString(Constants.USER_AVATAR_URL);

            result.add(new ListItemInfo(title.trim(), username.trim(), imageUrl.trim()));
        }

        return result;
    }

    private void selectAll(List<ListItemInfo> listItemInfo) {
        for (ListItemInfo itemInfo : listItemInfo) {
            itemInfo.setSelected(true);
        }
    }

    private void clearAll(List<ListItemInfo> listItemInfo) {
        for (ListItemInfo itemInfo : listItemInfo) {
            itemInfo.setSelected(false);
        }
    }

    private static class InfoLoader extends AsyncTaskLoader<String> {

        private static final String MAIN_URL = "https://api.github.com/repos/vmg/redcarpet/issues?state=closed";
        
        private String data = null;

        public InfoLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            if (data != null){
                deliverResult(data);
            }else {
                forceLoad();
            }
        }

        @Override
        public String loadInBackground() {
            String result = null;
            try {
                URL url = new URL(MAIN_URL);
                result = getResponseFromHttpUrl(url);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return result;
        }

        @Override
        public void deliverResult(String data) {
            this.data = data;
            super.deliverResult(data);
        }

        private String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try{
                InputStream in = connection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            }finally {
                connection.disconnect();
            }
        }
    }
}
