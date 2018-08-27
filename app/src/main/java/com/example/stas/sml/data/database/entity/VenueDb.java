package com.example.stas.sml.data.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value ={"venueId"}, unique = true)})
public class VenueDb {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public String venueId;

    public String address;

    public String workStatus;

    public boolean isOpen;

    public String imageUrl;

    public double longitude;

    public double latitude;

    public boolean saved;
}
