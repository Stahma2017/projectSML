package com.example.stas.sml.data.model.venuedetailedmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueDetailed {

    @SerializedName("response")
    @Expose
    private com.example.stas.sml.data.model.venuedetailedmodel.Response response;


    public com.example.stas.sml.data.model.venuedetailedmodel.Response getResponse() {
        return response;
    }

    public void setResponse(com.example.stas.sml.data.model.venuedetailedmodel.Response response) {
        this.response = response;
    }
}
