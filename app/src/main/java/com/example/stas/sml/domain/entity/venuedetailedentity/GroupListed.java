package com.example.stas.sml.domain.entity.venuedetailedentity;

import java.util.List;

public class GroupListed {

    private String type;

    private String name;

    private Integer count;

    private List<ItemListed> items = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ItemListed> getItems() {
        return items;
    }

    public void setItems(List<ItemListed> items) {
        this.items = items;
    }
}
