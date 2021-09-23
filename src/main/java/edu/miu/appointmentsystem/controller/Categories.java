package edu.miu.appointmentsystem.controller;

import edu.miu.appointmentsystem.domain.Category;

import java.util.Collection;
import java.util.List;

public class Categories {
    private Collection<Category> categories;

    public Categories(Collection<Category> categories) {
        this.categories = categories;
    }
    public Collection<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
