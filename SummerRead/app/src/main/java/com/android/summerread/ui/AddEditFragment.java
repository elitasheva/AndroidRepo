package com.android.summerread.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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

public class AddEditFragment extends Fragment {

    private CustomViewModel viewModel;

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
        View rootView = inflater.inflate(R.layout.add_edit_fragment, container, false);

        final EditText titleText = rootView.findViewById(R.id.edit_title);
        final EditText urlText = rootView.findViewById(R.id.edit_url);
        final EditText descriptionText = rootView.findViewById(R.id.edit_desc);
        final Spinner status = rootView.findViewById(R.id.edit_status);

        viewModel = ViewModelProviders
                .of(this, new CustomViewModelFactory(getActivity().getApplication()))
                .get(CustomViewModel.class);

        Button persist = rootView.findViewById(R.id.edit_persist);
        final Long selectedBookId = DetailsFragmentArgs.fromBundle(getArguments()).getId();
        if (selectedBookId == -1){
            //add
            persist.setText("Add");
        }else {
            //edit
            persist.setText("Edit");
            viewModel.getSelectedBook(selectedBookId).observe(this, new Observer<Book>() {
                @Override
                public void onChanged(Book book) {
                    titleText.setText(book.getTitle());
                    urlText.setText(book.getImgUrl());
                    descriptionText.setText(book.getDescription());

                    if (book.getStatus()){
                        status.setSelection(0);
                    }else {
                        status.setSelection(1);
                    }
                }
            });
        }

        //TODO add data validation
        persist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book newBook = new Book();
                newBook.setTitle(titleText.getEditableText().toString());
                newBook.setImgUrl(urlText.getEditableText().toString());
                newBook.setDescription(descriptionText.getEditableText().toString());
                newBook.setStatus(status.getSelectedItemPosition() == 0);

                if (selectedBookId == -1){
                    //add
                    viewModel.insertBook(newBook);
                }else {
                    //edit
                    newBook.setId(selectedBookId);
                    viewModel.updateBook(newBook);
                }

                Navigation
                        .findNavController(getActivity(), R.id.navHostFragment)
                        .navigate(AddEditFragmentDirections.actionAddEditFragmentToListFragment2());
            }
        });

        final ImageView previewImg = rootView.findViewById(R.id.edit_img_preview);
        previewImg.setVisibility(View.GONE);
        Button previewBtn = rootView.findViewById(R.id.edit_show_preview);
        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previewImg.setVisibility(View.VISIBLE);
                GlideApp.with(getContext())
                        .load(urlText.getEditableText().toString())
                        .override(100, 200)
                        .fitCenter()
                        .error(R.mipmap.ic_launcher)
                        .into(previewImg);
            }
        });

        return rootView;
    }

}
