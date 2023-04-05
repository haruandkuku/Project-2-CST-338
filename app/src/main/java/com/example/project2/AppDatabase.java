package com.example.project2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        version = 2,
        entities = {User.class}
)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Semester1.db";
    public static final String USER_TABLE = "user";
    public static volatile AppDatabase instance;
    public abstract UserDao userDao();
    public static final Object LOCK = new Object();
    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
