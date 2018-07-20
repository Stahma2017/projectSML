package com.example.stas.sml.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("venuePage")
    @Expose
    private VenuePage venuePage;
    @SerializedName("bestPhoto")
    @Expose
    private BestPhoto bestPhoto;
    @SerializedName("photos")
    @Expose
    private Photos photos;


    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public VenuePage getVenuePage() {
        return venuePage;
    }

    public void setVenuePage(VenuePage venuePage) {
        this.venuePage = venuePage;
    }

    public BestPhoto getBestPhoto() {
        return bestPhoto;
    }

    public void setBestPhoto(BestPhoto bestPhoto) {
        this.bestPhoto = bestPhoto;
    }
}
