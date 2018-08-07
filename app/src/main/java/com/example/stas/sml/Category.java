package com.example.stas.sml;

public class Category {
    private int categoryImage;
    private String categoryName;
    private String categoryId;

    public Category(int categoryImage, String categoryName, String categoryId) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
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
}
