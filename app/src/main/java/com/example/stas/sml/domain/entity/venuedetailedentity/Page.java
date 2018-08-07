package com.example.stas.sml.domain.entity.venuedetailedentity;

import com.example.stas.sml.data.model.venuedetailedmodel.PageInfo;
import com.example.stas.sml.data.model.venuedetailedmodel.User_;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    private User_ user;

    private PageInfo pageInfo = new PageInfo();

    public User_ getUser() {
        return user;
    }

    public void setUser(User_ user) {
        this.user = user;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
