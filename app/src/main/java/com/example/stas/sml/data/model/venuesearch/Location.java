package com.example.stas.sml.data.model.venuesearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {


  //Форматни, чтобы не было таких пробелов больших

    @SerializedName("distance")
    @Expose
    private Integer distance;

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

}


