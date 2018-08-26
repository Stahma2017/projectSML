package com.example.stas.sml.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.stas.sml.data.database.entity.VenueDb;

import java.util.List;

@Dao
public interface VenueDao {

    @Insert
    void insert(VenueDb venueDb);

    @Update
    void update(VenueDb venueDb);

    @Delete
    void delete(VenueDb venueDb);

    @Query("SELECT * FROM venuedb")
    List<VenueDb> getAll();
}
