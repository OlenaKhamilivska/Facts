package com.example.numbersapi.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Numbers {
    @NonNull
    @PrimaryKey
    public int number;
    public long time;
    public String facts;
    public int getNumber() {
        return number;
    }
    public String getFacts() {
        return facts;
    }
}
