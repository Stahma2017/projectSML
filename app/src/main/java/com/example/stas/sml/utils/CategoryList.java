package com.example.stas.sml.utils;

import com.example.stas.sml.Category;
import com.example.stas.sml.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryList {

    private static CategoryList instance;

    private List<Category> categories = null;


    public static CategoryList getInstance(){
        if (instance == null){
            instance = new CategoryList();
        }
        return instance;
    }

    public CategoryList() {
        categories = new ArrayList<>();
        populateCategories();
    }

    private void populateCategories(){
        categories = new ArrayList<>();
        categories.add(new Category(R.drawable.ic_category_travel_not_clicked, "Путешествия", "4d4b7105d754a06379d81259", R.drawable.ic_category_travel_clicked,R.drawable.travel_category));
        categories.add(new Category(R.drawable.ic_category_entertainment_not_clicked, "Развлечения", "4d4b7104d754a06370d81259", R.drawable.ic_category_entertainment_clicked, R.drawable.entertainment_category));
        categories.add(new Category(R.drawable.ic_category_event_not_clicked, "События", "4d4b7105d754a06373d81259", R.drawable.ic_category_event_clicked, R.drawable.event_category));
        categories.add(new Category(R.drawable.ic_category_shops_not_clicked, "Магазины", "4d4b7105d754a06378d81259", R.drawable.ic_category_shops_clicked, R.drawable.shops_category));
        categories.add(new Category(R.drawable.ic_category_food_not_clicked, "Рестораны", "4d4b7105d754a06374d81259", R.drawable.ic_category_food_clicked, R.drawable.food_category));
        categories.add(new Category(R.drawable.ic_category_nightlife_not_clicked, "Бары", "4d4b7105d754a06376d81259", R.drawable.ic_category_nightlife_clicked, R.drawable.nightlife_category));
        categories.add(new Category(R.drawable.ic_category_parks_outdoors_not_clicked, "Парки", "4d4b7105d754a06377d81259", R.drawable.ic_category_parks_outdoors_clicked, R.drawable.outdoors_category));
        categories.add(new Category(R.drawable.ic_category_education_not_clicked, "Университеты", "4d4b7105d754a06372d81259", R.drawable.ic_category_education_clicked, R.drawable.education_category));
        categories.add(new Category(R.drawable.ic_category_building_not_clicked, "Услуги", "4d4b7105d754a06375d81259", R.drawable.ic_category_building_clicked, R.drawable.building_category));

    }
    public List<Category> getCategoryList(){
        return categories;
    }
}
