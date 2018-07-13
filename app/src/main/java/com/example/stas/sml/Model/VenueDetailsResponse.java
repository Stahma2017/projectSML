package com.example.stas.sml.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueDetailsResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private VenueDto response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public VenueDto getVenueDto() {
        return response;
    }

    public void setVenueDto(VenueDto response) {
        this.response = response;
    }

}
