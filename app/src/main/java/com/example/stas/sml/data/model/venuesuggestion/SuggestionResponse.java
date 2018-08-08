package com.example.stas.sml.data.model.venuesuggestion;

import com.example.stas.sml.data.model.venuesearch.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuggestionResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
