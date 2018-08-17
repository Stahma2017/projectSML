package com.example.stas.sml.data.model.venuedetailedmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {


    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("pageInfo")
    @Expose
    private PageInfo pageInfo = new PageInfo();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
