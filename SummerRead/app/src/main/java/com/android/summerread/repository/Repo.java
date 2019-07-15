package com.android.summerread.repository;

import androidx.lifecycle.LiveData;

import com.android.summerread.database.Book;

import java.util.List;

public interface Repo {

    void insert(Book book);

    void update(Book book);

    void delete(Book book);

    LiveData<Book> getBook(Long id);

    LiveData<List<Book>> getBooks();
}
