package com.android.summerread.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.summerread.database.Book;
import com.android.summerread.repository.Repo;

import java.util.List;

public class CustomViewModel extends ViewModel {

    private Repo repo;

    private LiveData<List<Book>> books;

    private LiveData<Book> selectedBook;

    public CustomViewModel(Repo repo) {
        this.repo = repo;
        books = repo.getBooks();
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }

    public LiveData<Book> getSelectedBook(Long id){
        selectedBook = repo.getBook(id);
        return selectedBook;
    }

    public void deleteBook(Book book){
        repo.delete(book);
    }

    public void updateBook(Book book){
        repo.update(book);
    }

    public void insertBook(Book book) {
        repo.insert(book);
    }
}
