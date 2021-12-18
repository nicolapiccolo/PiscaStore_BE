package it.unito.user_service.service;

import it.unito.user_service.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listCategories();
    Category findCategory(long id);
    void createCategory(Category category);
}
