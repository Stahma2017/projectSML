package com.example.stas.sml.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchVenueModel {
    public SearchVenueModel(String ll) {
        this.ll = ll;
    }

    @SerializedName("ll")
    @Expose
    private String ll;

    public String getLl() {
        return ll;
    }

    public void setLl(String ll) {
        this.ll = ll;
    }
}
