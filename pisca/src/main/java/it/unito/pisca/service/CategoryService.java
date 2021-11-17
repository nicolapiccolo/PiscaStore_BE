package it.unito.pisca.service;

import it.unito.pisca.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listCategories();
    Category findCategory(long id);
    void createCategory(Category category);
}
