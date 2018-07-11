package com.example.stas.sml.VenueDetailedModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class VenueDetailed {

    @SerializedName("response")
    @Expose
    private com.example.stas.sml.VenueDetailedModel.Response response;


    public com.example.stas.sml.VenueDetailedModel.Response getResponse() {
        return response;
    }

    public void setResponse(com.example.stas.sml.VenueDetailedModel.Response response) {
        this.response = response;
    }
}
