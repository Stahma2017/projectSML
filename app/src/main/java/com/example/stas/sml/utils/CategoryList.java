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
        categories.add(new Category(R.drawable.ic_category_travel, "Путешествия и транспорт", "4d4b7105d754a06379d81259"));
        categories.add(new Category(R.drawable.ic_category_education, "Высшие учебные заведения", "4d4b7105d754a06372d81259"));
        categories.add(new Category(R.drawable.ic_category_entertainment, "Искусство и развлечения", "4d4b7104d754a06370d81259"));
        categories.add(new Category(R.drawable.ic_category_event, "События", "4d4b7105d754a06373d81259"));
        categories.add(new Category(R.drawable.ic_category_food, "Кафе и рестораны", "4d4b7105d754a06374d81259"));
        categories.add(new Category(R.drawable.ic_category_nightlife, "Ночные заведения", "4d4b7105d754a06376d81259"));
        categories.add(new Category(R.drawable.ic_category_parks_outdoors, "Заведения на свежем воздухе", "4d4b7105d754a06377d81259"));
        categories.add(new Category(R.drawable.ic_category_building, "Услуги и учреждения", "4d4b7105d754a06375d81259"));
        categories.add(new Category(R.drawable.ic_category_shops, "Магазины и услуги", "4d4b7105d754a06378d81259"));
    }
    public List<Category> getCategoryList(){
        return categories;
    }
}
