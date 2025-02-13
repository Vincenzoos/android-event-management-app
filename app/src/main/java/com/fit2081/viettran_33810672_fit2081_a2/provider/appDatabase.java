package com.fit2081.viettran_33810672_fit2081_a2.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CategoryEntity.class, EventEntity.class}, version = 1)
public abstract class appDatabase extends RoomDatabase {

    // database name, this is important as data is contained inside a file named "my_database"
    public static final String DATABASE_NAME = "my_database";
    public abstract appDAO appDAO();

    private static final int NUMBER_OF_THREADS = 4;
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile appDatabase INSTANCE;

    // ExecutorService is a JDK API that simplifies running tasks in asynchronous mode.
    // Generally speaking, ExecutorService automatically provides a pool of threads and an API
    // for assigning tasks to it.
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    // Initialize the database

    /**
     * Since this class is an abstract class, to get the database reference we would need
     * to implement a way to get reference to the database.
     *
     * @param context Application of Activity Context
     * @return a reference to the database for read and write operation
     */

    static appDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            // Synchronized blocks or methods prevents thread interference and make sure that data is consistent.
            // It means that only one thread can access critical section by acquiring a lock.
            // Unless this thread release this lock, all other thread(s) will have to wait to acquire a lock.
            synchronized (appDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                appDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
