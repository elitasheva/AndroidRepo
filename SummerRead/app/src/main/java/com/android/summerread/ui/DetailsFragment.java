package com.android.summerread.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.android.summerread.R;
import com.android.summerread.database.Book;

public class DetailsFragment extends Fragment {

    private CustomViewModel viewModel;

    private Book selectedBook;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);

        final TextView titleText = rootView.findViewById(R.id.details_title);
        final ImageView imageView = rootView.findViewById(R.id.details_img);
        final TextView descriptionText = rootView.findViewById(R.id.details_desc);

        viewModel = ViewModelProviders
                .of(this, new CustomViewModelFactory(getActivity().getApplication()))
                .get(CustomViewModel.class);

        Long selectedBookId = DetailsFragmentArgs.fromBundle(getArguments()).getId();

        viewModel.getSelectedBook(selectedBookId).observe(this, new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                titleText.setText(book.getTitle());
                GlideApp.with(getContext())
                        .load(book.getImgUrl())
                        .override(200, 300)
                        .fitCenter()
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);
                descriptionText.setText(book.getDescription());
                selectedBook = book;
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.details_delete: {
                viewModel.deleteBook(selectedBook);
                if (getActivity() != null){
                    getActivity().onBackPressed();
                }
            }
            break;
            case R.id.details_edit: {
                if (getActivity() != null){
                    Navigation
                            .findNavController(getActivity(), R.id.navHostFragment)
                            .navigate(DetailsFragmentDirections.actionDetailsFragment2ToAddEditFragment(selectedBook.getId()));
                }
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
