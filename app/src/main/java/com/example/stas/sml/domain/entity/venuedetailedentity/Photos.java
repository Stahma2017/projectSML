package com.example.stas.sml.domain.entity.venuedetailedentity;

import java.util.List;

public class Photos {

    private Integer count;

    private List<Group> groups = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
