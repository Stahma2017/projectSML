package com.example.stas.sml.domain.entity.venuedetailedentity;

import com.example.stas.sml.data.model.venuedetailedmodel.PageInfo;
import com.example.stas.sml.data.model.venuedetailedmodel.User;

public class Page {

    private User user;

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
