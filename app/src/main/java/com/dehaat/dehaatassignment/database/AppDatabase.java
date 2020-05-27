package com.dehaat.dehaatassignment.database;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dehaat.dehaatassignment.dao.AuthorDao;
import com.dehaat.dehaatassignment.dao.BookDao;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.model.Book;
import com.dehaat.dehaatassignment.util.AppExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Author.class, Book.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AuthorDao authorDao();

    public abstract BookDao bookDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dehaat_database")
                            .addCallback(roomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void deleteData() {
        AuthorDao dao = INSTANCE.authorDao();
        BookDao bookDao = INSTANCE.bookDao();
        dao.deleteAll();
        bookDao.deleteAll();
    }


    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            AppExecutors.getInstance().backgroundExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    deleteData();
                }
            });
        }
    };

}
