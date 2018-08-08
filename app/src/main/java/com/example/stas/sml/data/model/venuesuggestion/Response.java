package com.example.stas.sml.data.model.venuesuggestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {


    @SerializedName("minivenues")
    @Expose
    private List<Minivenue> minivenues = null;

    public List<Minivenue> getMinivenues() {
        return minivenues;
    }

    public void setMinivenues(List<Minivenue> minivenues) {
        this.minivenues = minivenues;
    }

}
