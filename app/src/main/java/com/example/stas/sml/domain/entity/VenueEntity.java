package com.example.stas.sml.domain.entity;

import com.example.stas.sml.data.model.BestPhoto;
import com.example.stas.sml.data.model.Category;
import com.example.stas.sml.data.model.Group;
import com.example.stas.sml.data.model.Item;
import com.example.stas.sml.data.model.Location;
import com.example.stas.sml.data.model.Photos;
import com.example.stas.sml.data.model.VenuePage;
import com.example.stas.sml.utils.UrlHelper;

import java.util.ArrayList;
import java.util.List;

public class VenueEntity {

    private String id;
    private String name;
    private Location location;
    private List<Category> categories = null;
    private VenuePage venuePage;
    private BestPhoto bestPhoto;
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

    public List<String> getPhotosUrls(){
        List<String> urls = new ArrayList<>();
        for(Group group: photos.getGroups()){
            Item item = group.getItems().get(0);
            urls.add(UrlHelper.getUrlToPhoto(item.getPrefix(), item.getSuffix()));
        }
        return urls;
    }

}
