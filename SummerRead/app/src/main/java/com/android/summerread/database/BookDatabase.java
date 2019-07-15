package com.android.summerread.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    private static volatile BookDatabase INSTANCE;

    public static BookDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (BookDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class,
                            "book_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BookDao bookDao;

        PopulateDbAsync(BookDatabase db) {
            bookDao = db.bookDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            List<Book> books = dummyData();
            for (Book book : books) {
                bookDao.insertBook(book);
            }
            return null;
        }

        private List<Book> dummyData(){
            List<Book> books = new ArrayList<>();
            Book book1 = new Book();
            book1.setTitle("Harry Potter and the Philosopher's Stone");
            book1.setImgUrl("https://images-na.ssl-images-amazon.com/images/I/41lnLrvBnML.jpg");
            book1.setDescription("Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle. Then, on Harry's eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin!");
            book1.setStatus(false);
            books.add(book1);

            Book book2 = new Book();
            book2.setTitle("Harry Potter and the Order of the Phoenix");
            book2.setImgUrl("https://images-na.ssl-images-amazon.com/images/I/51-zRYQweBL.jpg");
            book2.setDescription("Dark times have come to Hogwarts. After the Dementors' attack on his cousin Dudley, Harry Potter knows that Voldemort will stop at nothing to find him. There are many who deny the Dark Lord's return, but Harry is not alone: a secret order gathers at Grimmauld Place to fight against the Dark forces. Harry must allow Professor Snape to teach him how to protect himself from Voldemort's savage assaults on his mind. But they are growing stronger by the day and Harry is running out of time...");
            book2.setStatus(false);
            books.add(book2);

            Book book3 = new Book();
            book3.setTitle("Harry Potter and the Deathly Hallows");
            book3.setImgUrl("http://prodimage.images-bn.com/pimages/9781781102435_p0_v3_s1200x630.jpg");
            book3.setDescription("As he climbs into the sidecar of Hagrid's motorbike and takes to the skies, leaving Privet Drive for the last time, Harry Potter knows that Lord Voldemort and the Death Eaters are not far behind. The protective charm that has kept Harry safe until now is broken, but he cannot keep hiding. The Dark Lord is breathing fear into everything Harry loves and to stop him Harry will have to find and destroy the remaining Horcruxes. The final battle must begin - Harry must stand and face his enemy...");
            book3.setStatus(false);
            books.add(book3);

            return books;
        }
    }

}

