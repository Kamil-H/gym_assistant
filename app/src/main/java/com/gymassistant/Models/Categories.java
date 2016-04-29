package com.gymassistant.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-03-22.
 */
public class Categories {
    private List<Category> Categories = new ArrayList<Category>();

    public List<Category> getCategories() {
        return Categories;
    }

    public void setCategories(List<Category> categories) {
        Categories = categories;
    }
}
