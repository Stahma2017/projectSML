package com.example.stas.sml.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.stas.sml.data.database.dao.VenueDao;
import com.example.stas.sml.data.database.entity.VenueDb;

@Database(entities = {VenueDb.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VenueDao venueDao();
}
