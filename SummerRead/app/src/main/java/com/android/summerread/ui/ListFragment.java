package com.android.summerread.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.summerread.R;
import com.android.summerread.database.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListFragment extends Fragment implements CustomAdapter.OnRecyclerViewItemClicked {

    private CustomViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        final RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), calculateNoOfColumns(getContext()));
        recyclerView.setLayoutManager(layoutManager);
        final TextView emptyText = rootView.findViewById(R.id.text_empty);

        final CustomAdapter adapter = new CustomAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders
                .of(this, new CustomViewModelFactory(getActivity().getApplication()))
                .get(CustomViewModel.class);

        viewModel.getBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                if (books.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyText.setVisibility(View.GONE);
                    adapter.setBooks(books);
                }

            }
        });

        FloatingActionButton addBtn = rootView.findViewById(R.id.add_book);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation
                        .findNavController(getActivity(), R.id.navHostFragment)
                        .navigate(ListFragmentDirections.actionListFragment2ToAddEditFragment(-1));
            }
        });

        return rootView;
    }

    @Override
    public void onImageClick(Long id) {
        Navigation
                .findNavController(getActivity(), R.id.navHostFragment)
                .navigate(ListFragmentDirections.actionListFragment2ToDetailsFragment2(id));
    }

    @Override
    public void onReadMarkClick(Book book) {
        viewModel.updateBook(book);
    }

    private int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2) {
            noOfColumns = 2;
        }
        return noOfColumns;
    }
}