package com.example.numbersapi.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Numbers.class}, version = 2)

public abstract class AppDatabase extends RoomDatabase {
    public abstract NumbersDao numbersDao();
}
