package com.android.summerread.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    //update
    @Update
    void updateBook(Book book);

    //delete
    @Delete
    void deleteBook(Book book);

    //get all
    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAllBooks();

    //get one by id
    @Query("SELECT * FROM books WHERE book_id = :id")
    LiveData<Book> getBookById(Long id);
}
