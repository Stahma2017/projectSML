package com.example.stas.sml.data.model.venuedetailedmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_ {

    // Не по стандарту именования нужно убрать _

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("type")
    @Expose
    private String type;


    @SerializedName("homeCity")
    @Expose
    private String homeCity;
    @SerializedName("bio")
    @Expose
    private String bio;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
