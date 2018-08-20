package com.example.stas.sml;

public class Category {
    private int categoryImage;
    private int categoryImageEnabled;
    private String categoryName;
    private String categoryId;
    private boolean isEnabled = false;


    public Category(int categoryImage, String categoryName, String categoryId, int categoryImageEnabled) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.categoryImageEnabled = categoryImageEnabled;
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
}
