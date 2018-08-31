package com.example.stas.sml;

public class Category {
    private int categoryImage;
    private int categoryImageEnabled;
    private String categoryName;
    private String categoryId;
    private Integer markerColor;
    private boolean isEnabled = false;


    public Category(int categoryImage, String categoryName, String categoryId, int categoryImageEnabled, int markerColor) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.categoryImageEnabled = categoryImageEnabled;
        this.markerColor = markerColor;
    }
    public Category(String categoryId){
        this.categoryId = categoryId;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getCategoryImageEnabled() {
        return categoryImageEnabled;
    }

    public void setCategoryImageEnabled(int categoryImageEnabled) {
        this.categoryImageEnabled = categoryImageEnabled;
    }

    public Integer getMarkerColor() {
        return markerColor;
    }

    public void setMarkerColor(Integer markerColor) {
        this.markerColor = markerColor;
    }
}
