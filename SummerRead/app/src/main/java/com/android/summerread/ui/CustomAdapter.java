package com.android.summerread.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.summerread.R;
import com.android.summerread.database.Book;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Book> books;

    private OnRecyclerViewItemClicked listener;

    public interface OnRecyclerViewItemClicked {
        void onImageClick(Long id);
        void onReadMarkClick(Book book);
    }

    public CustomAdapter(OnRecyclerViewItemClicked listener) {
        this.listener = listener;
        this.books = new ArrayList<>();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_view, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Book current = books.get(position);
        holder.bindHolder(current);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleText;
        private ImageView imageView;
        private CheckedTextView readMark;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.row_title);
            imageView = itemView.findViewById(R.id.row_img);
            imageView.setOnClickListener(this);
            readMark = itemView.findViewById(R.id.row_read_mark);
            readMark.setOnClickListener(this);
        }

        void bindHolder(Book book){
            titleText.setText(book.getTitle());
            GlideApp.with(itemView.getContext())
                    .load(book.getImgUrl())
                    .override(100, 200)
                    .fitCenter()
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);

            if (book.getStatus()){
                readMark.setChecked(true);
                readMark.setCheckMarkDrawable(R.drawable.ic_checked);
            }else {
                readMark.setChecked(false);
                readMark.setCheckMarkDrawable(R.drawable.ic_unchecked);
            }

        }

        @Override
        public void onClick(View view) {
            if (listener != null){
                switch (view.getId()){
                    case R.id.row_img: {
                        listener.onImageClick(books.get(getAdapterPosition()).getId());
                    }
                    break;
                    case R.id.row_read_mark:{
                        Book current = books.get(getAdapterPosition());
                        current.setStatus(!readMark.isChecked());
                        listener.onReadMarkClick(current);
                    }
                    break;
                }
            }
        }
    }
}
