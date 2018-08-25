package com.example.stas.sml.domain.entity.venuedetailedentity;

import java.util.List;

public class Listed {

    private Integer count;

    private List<GroupListed> groups = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<GroupListed> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupListed> groups) {
        this.groups = groups;
    }
}
