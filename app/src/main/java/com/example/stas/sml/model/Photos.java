package com.example.stas.sml.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos  {

    @SerializedName("groups")
    @Expose
    private List<Group_> groups = null;

    public List<Group_> getGroups() {
        return groups;
    }

    public void setGroups(List<Group_> groups) {
        this.groups = groups;
    }
}
