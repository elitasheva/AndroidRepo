package com.android.summerread.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.android.summerread.database.Book;
import com.android.summerread.database.BookDao;
import java.util.List;

public class BookRepo implements Repo {

    private BookDao bookDao;

    private LiveData<List<Book>> books;

    private LiveData<Book> book;

    public BookRepo(BookDao bookDao) {
       this.bookDao = bookDao;
        getBooks();
    }

    public LiveData<Book> getBook(Long id) {
        book = bookDao.getBookById(id);
        return book;
    }

    public LiveData<List<Book>> getBooks() {
        books = bookDao.getAllBooks();
        return books;
    }

    public void insert(Book book){
        new InsertAsyncTask(bookDao).execute(book);
    }

    public void update(Book book){
        new UpdateAsyncTask(bookDao).execute(book);
    }

    public void delete(Book book) {
        new DeleteAsyncTask(bookDao).execute(book);
    }

    private static class InsertAsyncTask extends AsyncTask<Book, Void, Void> {

        private BookDao bookDao;

        public InsertAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.insertBook(books[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Book, Void, Void> {

        private BookDao bookDao;

        public UpdateAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.updateBook(books[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Book, Void, Void> {

        private BookDao bookDao;

        public DeleteAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.deleteBook(books[0]);
            return null;
        }
    }


}
