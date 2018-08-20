package com.example.stas.sml.data.model.venuedetailedmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hours {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("isOpen")
    @Expose
    private Boolean isOpen = false;

    @SerializedName("isLocalHoliday")
    @Expose
    private Boolean isLocalHoliday;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Boolean getLocalHoliday() {
        return isLocalHoliday;
    }

    public void setLocalHoliday(Boolean localHoliday) {
        isLocalHoliday = localHoliday;
    }
}
